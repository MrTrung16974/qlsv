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
					<h2>Danh sách sinh viên lớp </h2>
				</div>

				<div class="search-container">
					<input type="text" class="search-input"
						placeholder="Tìm kiếm theo mã sv...">
					<button class="btn-search">Tìm</button>
				</div>

				<div class="table-container">
					<table class="table">
						<thead>
							<tr>
								<th>STT</th>
								<th>Mã sinh viên</th>
								<th>Tên sinh viên</th>
								<th>Địa chỉ</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${requestScope.c}" var="c" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${c.student.id}</td>
									<td>${c.student.name}</td>
									<td>${c.student.address}</td>
									<td>
									   <div class="action-icons">
										<a class="text-success" data-toggle="tooltip" title="Thông tin điểm sinh viên" href="${pageContext.request.contextPath}/teacher/diem-sinhvien?studentId=${c.student.id}"><i class="fa-solid fa-table"></i></a>
									   </div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	</script>


</body>
</html>
