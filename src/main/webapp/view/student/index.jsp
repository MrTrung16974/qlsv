<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="vi">
<%@ include file="layout/head.jsp"%>
<body>

	<%@ include file="layout/header.jsp"%>
	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="col-xl-8">
					<div class="card">
						<div class="card-body pt-3">
							<!-- Bordered Tabs -->

							<div class="tab-content pt-2">
								<div class="tab-pane fade show active profile-overview"
									id="profile-overview">
									<h5 class="card-title">Thông tin chi tiết</h5>

									<div class="row">
										<div class="col-lg-3 col-md-4 label">Họ và tên</div>
										<div class="col-lg-9 col-md-8">${st.name}</div>
									</div>
									<div class="row">
										<div class="col-lg-3 col-md-4 label">Ngày sinh</div>
										<div class="col-lg-9 col-md-8">${st.dateOfBirth}</div>
									</div>
									<div class="row">
										<div class="col-lg-3 col-md-4 label">Khóa học</div>
										<div class="col-lg-9 col-md-8">
											<fmt:formatDate value="${st.startYear}" pattern="yyyy" />
											-
											<fmt:formatDate value="${st.endYear}" pattern="yyyy" />
										</div>
									</div>

									<div class="row">
										<div class="col-lg-3 col-md-4 label">Email</div>
										<div class="col-lg-9 col-md-8">${st.email}</div>
									</div>

									<div class="row">
										<div class="col-lg-3 col-md-4 label">Số điện thoại</div>
										<div class="col-lg-9 col-md-8">${st.phone}</div>
									</div>

									<div class="row">
										<div class="col-lg-3 col-md-4 label">Địa chỉ</div>
										<div class="col-lg-9 col-md-8">${st.address}</div>
									</div>
									<div class="row">
										<a href="#" class="col-lg-3 col-md-4 label"
											data-bs-toggle="modal" data-bs-target="#changePasswordModal">
											Đổi mật khẩu </a>
									</div>
								</div>


							</div>
							<!-- End Bordered Tabs -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<div class="modal fade" id="changePasswordModal" tabindex="-1"
		aria-labelledby="changePasswordModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h5 class="modal-title" id="changePasswordModalLabel">Đổi mật
						khẩu</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Đóng"></button>
				</div>

				<!-- Modal Body -->
				<div class="modal-body">
					<!-- Your form goes here -->
					<form action="${pageContext.request.contextPath}/change-password"
						method="post">
						<div class="mb-3">
							<label for="currentPassword" class="form-label">Mật khẩu
								hiện tại</label> <input name="currentPassword" type="password"
								class="form-control" id="currentPassword" required>
						</div>

						<div class="mb-3">
							<label for="newPassword" class="form-label">Mật khẩu mới</label>
							<input name="newPassword" type="password" class="form-control"
								id="newPassword" required>
						</div>

						<div class="mb-3">
							<label for="confirmPassword" class="form-label">Xác nhận
								mật khẩu mới</label> <input name="confirmPassword" type="password"
								class="form-control" id="confirmPassword" required>
						</div>

						<c:if test="${not empty error}">
							<p style="color: red;">${error}</p>
						</c:if>

						<div class="text-center">
							<button type="submit" class="btn btn-primary">Đổi mật
								khẩu</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>

</body>
</html>
