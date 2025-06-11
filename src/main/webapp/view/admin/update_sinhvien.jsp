<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="vi">
<%@ include file="layout/head.jsp"%>
<body>

	<%@ include file="layout/header.jsp"%>

	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="content-header">
					<h2>Cập nhật giáo viên</h2>
				</div>
				<div class="card">
					<div class="card-body">
						<form class="row g-3"
							action="${pageContext.request.contextPath}/admin/update-sinhvien"
							method="post">
							<div class="col-md-6">
								<label class="form-label">Mã</label> <input
									class="form-control" name="id" value="${users.id}" readonly>
							</div>
							<div class="col-md-6">
								<label class="form-label">Tên</label> <input type="text"
									class="form-control" name="ten" value="${users.name}">
							</div>
							<div class="col-md-6">
								<label class="form-label">SDT</label> <input type="text"
									class="form-control" pattern="^0\d{9}$" title="Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số." name="sdt" required value="${users.phone}">
							</div>
							<div class="col-md-6">
								<label class="form-label">Email</label> <input type="email"
									class="form-control" name="email" required
									value="${users.email}">
							</div>
							<div class="col-md-6">
								<label class="form-label">Địa chỉ</label> <input type="text"
									class="form-control" name="diachi" required
									value="${users.address}">
							</div>
							<div class="col-md-6">
								<label class="form-label">Ngày sinh</label> <input type="date"
									class="form-control" name="ngaysinh"
									value="${users.dateOfBirth}">
							</div>
							<div class="col-md-6">
								<label class="form-label">Năm bắt đầu</label> <input
									type="date" class="form-control" name="starttime"
									value="${users.startYear}">
							</div>
							<div class="col-md-6">
								<label class="form-label">Năm kết thúc</label> <input
									type="date" class="form-control" name="endtime"
									value="${users.endYear}">
							</div>


							<div class="col-12">
								<input type="submit" class="btn btn-primary"
									value="Lưu thay đổi">
										<a href="${pageContext.request.contextPath}/admin/sinhvien"
											class="btn btn-success">Thoát</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
