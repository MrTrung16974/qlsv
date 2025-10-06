<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="vi">
<%@ include file="layout/head.jsp"%>
<body>

	<%@ include file="layout/header.jsp"%>
	<div class="container-fluid p-0">
		<div class="row m-0">
			<%@ include file="layout/sidebar.jsp"%>

			<div class="col-md-9 col-lg-9 main-content">
				<div class="content-header d-flex">
					<h2>Điểm số sinh viên</h2>
					<div class="close-icon"
						style="margin-left: 34rem; margin-top: 10px;">
						<a onclick="return confirmExit()" data-toggle="tooltip"
							title="Thông tin điểm sinh viên"><i
							class="fa-solid fa-circle-xmark"></i></a>
					</div>

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
								<th>Mã sinh viên</th>
								<th>Tên sinh viên</th>
								<th>Môn học</th>
								<th>Điểm quá trình</th>
								<th>Điểm thi cuối kỳ</th>
								<th>Điểm trung bình</th>
								<th>Thao tác</th>
							</tr>
						</thead>
						<tbody>
							<fmt:formatDate var="now" value="<%=new java.util.Date()%>"
								pattern="yyyy-MM-dd" />
							<c:forEach var="ss" items="${ss}" varStatus="loop">
								<tr>
								    <c:if test="${loop.index == 0}">
                                        <td rowspan="${fn:length(ss.student.id)}">${ss.student.id}</td>
                                    </c:if>
                                    <c:if test="${loop.index == 0}">
                                        <td rowspan="${fn:length(ss.student.name)}">${ss.student.name}</td>
                                    </c:if>
									<td>${ss.subject.name}</td>
									<td><c:choose>
											<c:when test="${ss.scoreProcess == 0.0}">
                                                Chưa nhập điểm
                                            </c:when>
											<c:otherwise>
                                                ${ss.scoreProcess}
                                            </c:otherwise>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${ss.scoreFinal == 0.0}">
                                                Chưa nhập điểm
                                            </c:when>
											<c:otherwise>
                                                ${ss.scoreFinal}
                                            </c:otherwise>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${ss.score_average == 0.0}">
                                                Chưa nhập điểm
                                            </c:when>
											<c:otherwise>
                                                ${ss.score_average}
                                            </c:otherwise>
										</c:choose></td>
									<td>
														<a href="#" class="text-success" data-bs-toggle="modal"
															data-bs-target="#addScoreModal"
															data-student-id="${ss.student.id}"
															data-subject-id="${ss.subject.id}"
															data-class-id="${classId}">
															<i class="fa-solid fa-plus"></i>
														</a>
														<a href="${pageContext.request.contextPath}/admin/delete-score?id=${ss.id}&classId=${classId}&studentId=${ss.student.id}" class="text-danger"><i class="fa-solid fa-trash"></i></a>
														</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${param.reopenModal == 'true'}">
		<script>
			document.addEventListener("DOMContentLoaded", function() {
				var addUserModal = new bootstrap.Modal(document
						.getElementById("addScoreModalLabel"));
				addUserModal.show();
			});
		</script>
	</c:if>
	<!-- Modal -->
	<div class="modal fade" id="addScoreModal" tabindex="-1"
		aria-labelledby="addScoreModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<form action="${pageContext.request.contextPath}/admin/add-score"
					method="post">
					<div class="modal-header">
						<h5 class="modal-title" id="addScoreModalLabel">Thêm mới điểm
							sinh viên</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Đóng"></button>
					</div>

					<div class="modal-body">
						<!-- Hidden fields -->
						<input type="hidden" name="studentId" id="modalStudentId">
						<input type="hidden" name="subjectId" id="modalSubjectId">
						<input type="hidden" name="classId" id="modalClassId">
						<div class=" g-3">
							<!-- g-3 thêm khoảng cách giữa các cột -->
							<div class="g-3">
								<!-- Thêm class 'row' để các col-md-4 nằm cùng hàng -->
								<div class="form-group">
									<label class="form-label">Điểm quá trình</label> <input
										type="number" min="0" max="10" class="form-control"
										name="chuyencan" required>
								</div>
								<div class="form-group">
									<label class="form-label">Điểm thi</label> <input type="number"
										min="0" max="10" class="form-control" name="thi" required>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">Lưu điểm</button>
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Đóng</button>
						</div>
				</form>
			</div>
		</div>
	</div>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
	</script>
	<script>
		function confirmExit() {
			const isConfirmed = confirm("Bạn có chắc chắn muốn thoát khỏi trang này?");
			if (isConfirmed) {
				window.location.href = "http://localhost:8080/QLSV/teacher/thongtin-lop";
			}
			return false; // Ngăn chặn <a> tự reload trang nếu cancel
		}

		const addScoreModal = document.getElementById('addScoreModal'); // lấy modal theo id
		addScoreModal.addEventListener('show.bs.modal', function(event) {
			const button = event.relatedTarget; // nút vừa được click để mở modal
			const studentId = button.getAttribute('data-student-id');
			const subjectId = button.getAttribute('data-subject-id');
			const classId = button.getAttribute('data-class-id');
			document.getElementById('modalStudentId').value = studentId; // gán vào input hidden
			document.getElementById('modalSubjectId').value = subjectId;
			document.getElementById('modalClassId').value = classId;
		});
	</script>

</body>
</html>
