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
				<div class="content-header d-flex">
					<h2>Thay đổi điểm sinh viên</h2>
					<div class="close-icon" style="margin-left: auto; margin-top: 10px;">
						<a onclick="return confirmExit()" data-toggle="tooltip" title="Quay lại">
							<i class="fa-solid fa-circle-xmark"></i>
						</a>
					</div>
				</div>

				<div class="card mt-4">
					<div class="card-body">
						<c:if test="${not empty error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>

						<form class="row g-3"
							action="${pageContext.request.contextPath}/teacher/update-score"
							method="post">

							<input type="hidden" name="ssid" value="${ss.id}">
							<input type="hidden" name="sid" value="${ss.subject.id}">
							<input type="hidden" name="scoreId" value="${ss.student.id}">

							<div class="col-md-4">
								<label class="form-label">Điểm chuyên cần</label>
								<input type="number" min="0" max="10" step="1"
									class="form-control" name="chuyencan" required
									value="${ss.scoreProcess}">
							</div>

							<div class="col-md-4">
								<label class="form-label">Điểm thi</label>
								<input type="number" min="0" max="10" step="1"
									class="form-control" name="thi" required
									value="${ss.scoreFinal}">
							</div>

							<div class="col-12 mt-4">
								<button type="submit" class="btn btn-primary">Thay đổi</button>
								<a href="${pageContext.request.contextPath}/teacher/diem-sinhvien?studentId=${studentId}"
									class="btn btn-secondary">Thoát</a>
							</div>

						</form>
					</div>
				</div>
			</div>

		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	<script>
		function confirmExit() {
			const isConfirmed = confirm("Bạn có chắc chắn muốn thoát khỏi trang này?");
			if (isConfirmed) {
				window.location.href = "http://localhost:8080/QLSV/teacher/thongtin-lop";
			}
			return false;
		}
	</script>
</body>
</html>
