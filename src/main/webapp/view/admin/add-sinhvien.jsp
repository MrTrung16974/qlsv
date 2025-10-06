<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="vi">
<%@ include file="layout/head.jsp"%>
<body>

	<%@ include file="layout/header.jsp"%>
	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Thêm mới sinh viên</h5>
						<form class="row g-3" action="${pageContext.request.contextPath}/admin/add-sinhvien" method="post">
							<div class="col-md-6">
								<label class="form-label">Mã</label>
								<input type="text" class="form-control" name="id" required>
							</div>
							<div class="col-md-6">
								<label class="form-label">Tên</label>
								<input type="text" class="form-control" name="ten" required>
							</div>
							<div class="col-md-6">
								<label class="form-label">SDT</label>
								<input type="text" pattern="^0\d{9}$" title="Số điện thoại phải bắt đầu bằng 0 và có 10 chữ số." class="form-control" required="required" name="sdt">
							</div>
							<div class="col-md-6">
								<label class="form-label">Email</label>
								<input type="email" class="form-control" required="required" name="email">
							</div>
							<div class="col-md-6">
								<label class="form-label">Địa chỉ</label>
								<input type="text" class="form-control" required="required" name="diachi">
							</div>
							<div class="col-md-6">
								<label class="form-label">Ngày sinh</label>
								<input type="date" class="form-control" name="ngaysinh" required>
							</div>
							<div class="col-md-6">
								<label class="form-label">Ngày bắt đầu</label>
								<input type="date" class="form-control" name="starttime" required>
							</div>
							<div class="col-md-6">
								<label class="form-label">Ngày kết thúc</label>
								<input type="date" class="form-control" name="endtime" required>
							</div>

							<!-- Nút Thêm mới và Thoát -->
							<div class="col-12">
								<input type="submit" class="btn btn-primary" value="Thêm mới">
								<a href="${pageContext.request.contextPath}/admin/sinhvien" class="btn btn-secondary">Thoát</a>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
