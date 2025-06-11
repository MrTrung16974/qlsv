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
								<th>Điểm trung bình môn</th>
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
