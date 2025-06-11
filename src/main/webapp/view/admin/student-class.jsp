<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="vi">
<%@ include file="layout/head.jsp"%>
<body>

	<%@ include file="layout/header.jsp"%>
	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="content-header">
					<h2>Danh sách lớp học</h2>
				</div>

				<div class="search-container">
                    <form action="/qlsv/admin/lop-student" method="GET">
                        <input type="text" class="search-input" name="searchTerm" placeholder="Tìm kiếm theo mã hoặc tên sinh viên..." />
                        <input type="hidden" name="id" value="${clazz.id}" />  <!-- Hidden field to pass the class ID -->
                        <button class="btn-search" type="submit">Tìm</button>
                        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addStudentModal">Thêm mới</button>
                    </form>

                </div>

				<div class="table-container">
					<c:if test="${param.msg == 'deleted'}">
						<div class="alert alert-success alert-dismissible fade show"
							role="alert">
							✅ Xóa sinh viên khỏi lớp thành công!
							<button type="button" class="btn-close" data-bs-dismiss="alert"
								aria-label="Close"></button>
						</div>
					</c:if>
					<c:if test="${not empty sessionScope.successMessage}">
                    					<div class="alert alert-success alert-dismissible fade show"
                    						role="alert">
                    						<strong>✅</strong> ${sessionScope.successMessage}
                    						<button type="button" class="btn-close" data-bs-dismiss="alert"
                    							aria-label="Close"></button>
                    					</div>
                    					<c:remove var="successMessage" scope="session" />
                    				</c:if>
					<table class="table">
						<thead>
							<tr>
								<th>STT</th>
								<th>Mã sinh viên</th>
								<th>Tên sinh viên</th>
								<th>Địa chỉ</th>
								<th>Ngày sinh</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.cl}" var="cl" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${cl.student.id}</td>
									<td>${cl.student.name}</td>
									<td>${cl.student.address}</td>
									<td>${cl.student.dateOfBirth}</td>
									<td>
										<div class="action-icons">
											<a href="${pageContext.request.contextPath}/admin/diem-student?id=${cl.aClass.id}&studentId=${cl.student.id}" class="text-success"
												><i
												class="fa-solid fa-table"></i></a> <a href="#"
												class="text-danger" href="#"
												onclick="deleteStudentFromClass('${cl.student.id}', '${cl.aClass.id}')">
												<i class="fa-solid fa-trash"></i>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<c:if test="${param.reopenModal == 'true'}">
						<script>
							document
									.addEventListener(
											"DOMContentLoaded",
											function() {
												var addUserModal = new bootstrap.Modal(
														document
																.getElementById("addStudentModalLabel"));
												addUserModal.show();
											});
						</script>
					</c:if>
					<!-- Modal -->
					<div class="modal fade" id="addStudentModal" tabindex="-1"
						aria-labelledby="addStudentModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<!-- modal-lg để rộng hơn -->
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="addStudentModalLabel">Thêm mới
										sinh viên vào lớp học</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Đóng"></button>
								</div>
								<div class="modal-body">
									<form class="row g-3"
										action="${pageContext.request.contextPath}/admin/add-classstudent"
										method="post">
										<div class="col-md-12">
											<c:if test="${not empty param.error}">
												<p style="color: red;">${param.error}</p>
											</c:if>
											<label class="form-label">Lớp học</label> <select
												name="clazz" class="form-select">
												<option value="${clazz.id}" selected>${clazz.name}</option>
											</select>
										</div>
										<div class="form-group">
											<label class="form-label d-block">Sinh viên</label>
											<c:forEach items="${user}" var="u">
												<div class="form-check form-check-inline">
													<input class="form-check-input" type="checkbox"
														name="students" value="${u.id}" id="student${u.id}">
													<label class="form-check-label" for="student${u.id}">${u.name}</label>
												</div>
											</c:forEach>
										</div>
										<div class="col-12">
											<input type="submit" class="btn btn-primary" value="Thêm mới">
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">Thoát</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>



				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function deleteStudentFromClass(studentId, classId) {
			if (confirm("Bạn có chắc chắn muốn xóa sinh viên mã " + studentId
					+ " khỏi lớp ?")) {
				window.location = "/qlsv/admin/delete-sinhvien-lop?studentId="
						+ studentId + "&classId=" + classId;
			}
		}


	</script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	</script>
</body>
</html>
