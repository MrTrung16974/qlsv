<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="vi">
<head>
<%@ include file="layout/head.jsp"%>
</head>
<body>

	<%@ include file="layout/header.jsp"%>
	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="content-header">
					<h2>Danh sách giáo viên</h2>
				</div>

				<div class="search-container">
					<form method="get"
						action="${pageContext.request.contextPath}/admin/list-user">
						<input type="text" name="keyword" class="search-input"
							placeholder="Tìm kiếm theo tên..." value="${keyword}">
						<button class="btn-search" type="submit">Tìm</button>
						<a href="#" class="btn btn-success" " data-bs-toggle="modal"
							data-bs-target="#addUserModal">Thêm mới</a> <a href="#"
							class="btn btn-success" data-bs-toggle="modal"
							data-bs-target="#uploadModal">Thêm nhiều</a>
					</form>
				</div>
				<c:if test="${not empty sessionScope.successMessage}">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert">
						<strong>✅</strong> ${sessionScope.successMessage}
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
					<c:remove var="successMessage" scope="session" />
				</c:if>
				<div class="table-container">
					<table class="table">
						<thead>
							<tr>
								<th>STT</th>
								<th>Mã GV</th>
								<th>Tên</th>
								<th>SĐT</th>
								<th>Email</th>
								<th>Ngày sinh</th>
								<th>Trạng thái</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.usersList}" var="user"
								varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${user.id}</td>
									<td>${user.name}</td>
									<td>${user.phone}</td>
									<td>${user.email}</td>
									<td>${user.dateOfBirth}</td>
									<c:choose>
										<c:when test="${user.status}">
											<td class="text-danger">Đã khóa</td>
										</c:when>
										<c:otherwise>
											<td class="text-success">Hoạt động</td>
										</c:otherwise>
									</c:choose>
									<td>
										<div class="action-icons">
											<a
												href="${pageContext.request.contextPath}/admin/update-user?id=${user.id}"
												class="text-success"> <i
												class="fa-regular fa-pen-to-square"></i>
											</a>
											<c:choose>
												<c:when test="${user.status}">

													<a href="#" class="text-danger"
														onclick="confirmUnlock('${user.id}')"> <i
														class="fa-solid fa-lock"></i>
													</a>
												</c:when>
												<c:otherwise>
													<a href="#" class="text-success"
														onclick="confirmLock('${user.id}')"> <i
														class="fa-solid fa-lock-open"></i>
													</a>
												</c:otherwise>
											</c:choose>
											<a href="#" class="text-success"
												onclick="confirmResetPass('${user.id}')"><i
												class="fa-solid fa-rotate-left"></i></a> <a href="#"
												class="text-danger" onclick="confirmDelete('${user.id}')">
												<i class="fa-solid fa-trash"></i>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<nav aria-label="Page navigation example"
						style="margin-left: 38rem;">
						<ul class="pagination justify-content-center">

							<!-- Previous -->
							<li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
								<a class="page-link"
								href="?page=${currentPage - 1}&keyword=${keyword}"
								aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
							</a>
							</li>

							<!-- Pages -->
							<c:if test="${totalPages > 1}">
								<!-- Trang đầu và ... nếu cần -->
								<c:if test="${currentPage > 2}">
									<li class="page-item"><a class="page-link"
										href="?page=1&keyword=${keyword}">1</a></li>
									<li class="page-item disabled"><span class="page-link">...</span></li>
								</c:if>

								<!-- Các trang gần -->
								<c:forEach begin="${currentPage - 1}" end="${currentPage + 1}"
									var="i">
									<c:if test="${i >= 1 && i <= totalPages}">
										<li class="page-item ${i == currentPage ? 'active' : ''}">
											<a class="page-link" href="?page=${i}&keyword=${keyword}">${i}</a>
										</li>
									</c:if>
								</c:forEach>

								<!-- Trang cuối và ... nếu cần -->
								<c:if test="${currentPage < totalPages - 1}">
									<li class="page-item disabled"><span class="page-link">...</span></li>
									<li class="page-item"><a class="page-link"
										href="?page=${totalPages}&keyword=${keyword}">${totalPages}</a></li>
								</c:if>
							</c:if>

							<!-- Next -->
							<li
								class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
								<a class="page-link"
								href="?page=${currentPage + 1}&keyword=${keyword}"
								aria-label="Next"> <span aria-hidden="true">&raquo;</span>
							</a>
							</li>

						</ul>
					</nav>


					<c:if test="${param.reopenModal == 'true'}">
						<script>
							document
									.addEventListener(
											"DOMContentLoaded",
											function() {
												var addUserModal = new bootstrap.Modal(
														document
																.getElementById("addUserModal"));
												addUserModal.show();
											});
						</script>
					</c:if>
					<div class="modal fade" id="addUserModal" tabindex="-1"
						aria-labelledby="addUserModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="addUserModalLabel">Thêm mới
										giáo viên</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Đóng"></button>
								</div>
								<div class="modal-body">
									<!-- Form thêm người dùng -->
									<form id="addUserForm"
										action="${pageContext.request.contextPath}/admin/list-user"
										method="post">
										<div class="row g-3">
											<c:if test="${not empty param.error}">
												<p style="color: red;">${param.error}</p>
											</c:if>
											<div class="col-md-6">
												<label class="form-label">Mã</label> <input type="text"
													class="form-control" name="id" required>
											</div>
											<div class="col-md-6">
												<label class="form-label">Tên</label> <input type="text"
													class="form-control" name="ten" required>
											</div>
											<div class="col-md-6">
												<label class="form-label">SDT</label> <input type="text"
													class="form-control" pattern="^0\d{9}$"
													title="Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số."
													name="sdt" required>
											</div>
											<div class="col-md-6">
												<label class="form-label">Email</label> <input type="email"
													class="form-control" name="email" required>
											</div>
											<div class="col-md-6">
												<label class="form-label">Ngày sinh</label> <input
													type="date" class="form-control" name="ngaysinh" required>
											</div>
										</div>
										<div class="mt-4 text-end">
											<button type="submit" class="btn btn-primary">Thêm
												mới</button>
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">Thoát</button>
										</div>
									</form>
									<div id="alertMessage" class="mt-3"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="uploadModal" tabindex="-1"
		aria-labelledby="uploadModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Header -->
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="uploadModalLabel">Tải tệp
						Excel lên để thêm mới người dùng</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>

				<!-- Body -->
				<div class="modal-body">
				    <form action="/qlsv/download-excel" method="get">
                		<button type="submit">Tải file Excel mẫu</button>
                	</form>
					<form action="/qlsv/admin/uploads" method="post"
						enctype="multipart/form-data">
						<div class="mb-3">
							<label for="formFile" class="form-label">Chọn file Excel
								(.xlsx)</label> <input class="form-control" type="file" id="formFile"
								name="file" accept=".xlsx">
						</div>
						<button type="submit" class="btn btn-primary">Upload</button>
					</form>

				</div>

			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

	<script>
		function confirmDelete(userId) {
			if (confirm("Bạn có chắc chắn muốn xóa " + userId + " này?")) {
				window.location.href = "/qlsv/admin/delete-user?id=" + userId;
			}
		}

		function confirmLock(userId) {
			if (confirm("Bạn có chắc chắn muốn khóa tài khoản " + userId
					+ " này?")) {
				window.location.href = "/qlsv/admin/lock-user?id=" + userId;
			}
		}
		function confirmResetPass(userId) {
			if (confirm("Bạn có chắc chắn muốn reset mật khẩu " + userId
					+ " này?")) {
				window.location.href = "/qlsv/admin/reset-pass?id=" + userId;
			}
		}
		function confirmUnlock(userId) {
			if (confirm("Bạn có chắc chắn muốn mở khóa tài khoản " + userId
					+ " này?")) {
				window.location.href = "/qlsv/admin/unlock-user?id=" + userId;
			}
		}
	</script>


</body>
</html>
