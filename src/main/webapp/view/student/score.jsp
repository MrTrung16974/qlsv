<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="vi">
<%@ include file="layout/head.jsp"%>

<body>
<%@ include file="layout/header.jsp"%>
<div class="container-fluid p-0">
    <div class="row m-0">
        <%@ include file="layout/sidebar.jsp"%>

        <div class="col-md-9 col-lg-9 main-content p-4">
            <div class="card border-0 shadow-sm">
                <div class="card-body">
                    <h3 class="mb-4 fw-bold text-primary">
                        <i class="bi bi-mortarboard-fill me-2"></i>Bảng điểm sinh viên
                    </h3>

                    <!-- THỐNG KÊ ĐIỂM -->
                    <div class="row mb-4">
                        <div class="col-md-6 mb-3">
                            <div class="card border-0 shadow-sm">
                                <div class="card-body bg-primary text-white rounded-3">
                                    <h6 class="mb-1">🎯 Điểm tổng kết học kỳ này:</h6>
                                    <h4 class="fw-bold mb-0">
                                        <fmt:formatNumber value="${scoreByCycle}" maxFractionDigits="2"/>
                                    </h4>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <div class="card border-0 shadow-sm">
                                <div class="card-body bg-success text-white rounded-3">
                                    <h6 class="mb-1">🏆 Điểm trung bình toàn khóa:</h6>
                                    <h4 class="fw-bold mb-0">
                                        <fmt:formatNumber value="${diemTong}" maxFractionDigits="2"/>
                                    </h4>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- LỌC & EXPORT -->
                    <div class="row align-items-end mb-4">
                        <div class="col-md-4">
                            <form id="semesterForm" action="${pageContext.request.contextPath}/student/student-score" method="get">
                                <label for="semesterSelect" class="form-label fw-semibold">Chọn học kỳ:</label>
                                <select name="maHocKy" id="semesterSelect" class="form-select" onchange="this.form.submit()">
                                    <option value="all" <c:if test="${semesterId == 'all'}">selected</c:if>>Tất cả các học kỳ</option>
                                    <c:forEach items="${cycle}" var="semester">
                                        <option value="${semester.id}" <c:if test="${semesterId == semester.id}">selected</c:if>>${semester.name}</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </div>
                        <div class="col-md-8 d-flex justify-content-end " style="margin-bottom: 20px;">
                            <button class="btn btn-primary me-2" onclick="printGrades()">
                                <i class="bi bi-printer-fill me-1"></i> In bảng điểm
                            </button>
                            <a href="${pageContext.request.contextPath}/student/export-score?action=export-excel" class="btn btn-success">
                                <i class="bi bi-file-earmark-excel-fill me-1"></i> Xuất Excel
                            </a>
                        </div>
                    </div>

                    <!-- BẢNG ĐIỂM -->
                    <div class="table-responsive">
                        <table class="table table-hover align-middle table-bordered">
                            <thead class="table-light">
                                <tr class="text-center">
                                    <th scope="col">STT</th>
                                    <th scope="col">Tên học phần</th>
                                    <th scope="col">Học kỳ</th>
                                    <th scope="col">Điểm quá trình</th>
                                    <th scope="col">Điểm thi</th>
                                    <th scope="col">Điểm tổng kết</th>
                                    <th scope="col">Điểm chữ</th>
                                    <th scope="col">Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${ss}" var="s" varStatus="status">
                                    <tr class="text-center">
                                        <th scope="row">${status.index + 1}</th>
                                        <td class="text-start">${s.subject.name}</td>
                                        <td>${s.subject.cycle.name}</td>
                                        <td>${s.scoreProcess}</td>
                                        <td>${s.scoreFinal}</td>
                                        <td>${s.score_average}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${s.score_average >= 8.5}">A</c:when>
                                                <c:when test="${s.score_average >= 7.0}">B</c:when>
                                                <c:when test="${s.score_average >= 5.5}">C</c:when>
                                                <c:when test="${s.score_average >= 4.0}">D</c:when>
                                                <c:otherwise>F</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <c:choose>
                                            <c:when test="${s.score_average >= 5.0}">
                                                <c:set var="statusText" value="Đạt" />
                                                <c:set var="statusClass" value="bg-success" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="statusText" value="Chưa đạt" />
                                                <c:set var="statusClass" value="bg-danger" />
                                            </c:otherwise>
                                        </c:choose>
                                        <td><span class="badge ${statusClass}">${statusText}</span></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function printGrades() {
        window.print();
    }
</script>

<!-- Vendor JS Files -->
<script src="/view/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/view/assets/js/main.js"></script>
</body>
</html>
