<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="vi">
<head>
    <%@ include file="layout/head.jsp"%>
    <style>
        .card {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
            margin-bottom: 20px;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        .card .card-body {
            padding: 20px;
        }

        .card-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 15px;
            color: #333;
        }

        .card-icon {
            width: 55px;
            height: 55px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            margin-right: 15px;
        }

        .card .d-flex {
            align-items: center;
        }

        .card h6 {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .card .trend {
            font-size: 14px;
            display: flex;
            align-items: center;
        }

        .trend-up {
            color: #28a745;
        }

        .trend-down {
            color: #dc3545;
        }

        .info-card {
            background-color: #ffffff;
            border-left: 5px solid #5cb85c; /* Green border */
        }

        .revenue-card {
            background-color: #ffffff;
            border-left: 5px solid #f0ad4e; /* Orange border */
        }

        .customers-card {
            background-color: #ffffff;
            border-left: 5px solid #d9534f; /* Red border */
        }

        .course-card {
            background-color: #ffffff;
            border-left: 5px solid #0275d8; /* Blue border */
        }

        .attendance-card {
            background-color: #ffffff;
            border-left: 5px solid #5bc0de; /* Light blue border */
        }

        .main-content {
            padding: 25px;
            background-color: #f8f9fa;
        }

        .content-header {
            margin-bottom: 25px;
            border-bottom: 1px solid #dee2e6;
            padding-bottom: 15px;
        }

        .content-header h2 {
            font-weight: 600;
            color: #2c3e50;
        }

        .chart-container {
            background: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }

        .chart-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 20px;
            color: #333;
        }

        .table-container {
            background: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .stats-table {
            width: 100%;
            border-collapse: collapse;
        }

        .stats-table th {
            background-color: #f8f9fa;
            font-weight: 600;
            text-align: left;
            padding: 12px 15px;
            border-bottom: 2px solid #dee2e6;
        }

        .stats-table td {
            padding: 12px 15px;
            border-bottom: 1px solid #dee2e6;
        }

        .stats-table tr:hover {
            background-color: #f1f1f1;
        }

        .progress {
            height: 8px;
            margin-top: 5px;
        }

        .badge {
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: 500;
        }

        .filter-bar {
            background: #fff;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }

        .filter-group {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }

        .form-select, .form-control {
            border-radius: 5px;
            border: 1px solid #ced4da;
            padding: 8px 12px;
        }

        .btn-filter {
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 8px 15px;
            cursor: pointer;
        }

        .btn-filter:hover {
            background-color: #0069d9;
        }
    </style>
</head>
<body>

    <%@ include file="layout/header.jsp"%>

    <div class="container-fluid p-0">
        <div class="row m-0">
            <%@ include file="layout/sidebar.jsp"%>

            <div class="col-md-9 col-lg-9 main-content">
                <div class="content-header">
                    <h2>Thống kê Tổng quan</h2>
                </div>

                <!-- Main Statistics Cards -->
                <div class="row">
                    <!-- Thống kê học viên Card -->
                    <div class="col-xl-3 col-md-6">
                        <div class="card info-card">
                            <div class="card-body">
                                <h5 class="card-title">Học viên <span>| Tổng số</span></h5>
                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center" style="background-color: rgba(92, 184, 92, 0.1); color: #5cb85c;">
                                        <i class="fa-solid fa-user-graduate"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${student}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê giáo viên Card -->
                    <div class="col-xl-3 col-md-6">
                        <div class="card revenue-card">
                            <div class="card-body">
                                <h5 class="card-title">Giáo viên <span>| Tổng số</span></h5>
                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center" style="background-color: rgba(240, 173, 78, 0.1); color: #f0ad4e;">
                                        <i class="fa-solid fa-chalkboard-teacher"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${teacher}</h6>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê lớp học Card -->
                    <div class="col-xl-3 col-md-6">
                        <div class="card customers-card">
                            <div class="card-body">
                                <h5 class="card-title">Lớp học <span>| Tổng số</span></h5>
                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center" style="background-color: rgba(217, 83, 79, 0.1); color: #d9534f;">
                                        <i class="fa-solid fa-school"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${clazz}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thống kê khóa học Card -->
                    <div class="col-xl-3 col-md-6">
                        <div class="card course-card">
                            <div class="card-body">
                                <h5 class="card-title">Môn học <span>| Tổng số</span></h5>
                                <div class="d-flex align-items-center">
                                    <div class="card-icon rounded-circle d-flex align-items-center justify-content-center" style="background-color: rgba(2, 117, 216, 0.1); color: #0275d8;">
                                        <i class="fa-solid fa-book-open"></i>
                                    </div>
                                    <div class="ps-3">
                                        <h6>${course}3</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
				
				<div class="row statistics-section">
   <div class="col-lg-12">
       <div class="filter-bar">
           <div class="filter-group">
               <div>
                   <label for="semester" class="form-label">Học kỳ</label>
                   <select class="form-select" id="semester" onchange="filterByCycle()" style="width: 210px;">
                       <option value="all" ${empty param.cycleId ? "selected" : ""}>Tất cả học kỳ</option>
                       <c:forEach var="cycle" items="${cycles}">
                           <option value="${cycle.id}" ${cycle.id == param.cycleId ? "selected" : ""}>
                               ${cycle.name}
                           </option>
                       </c:forEach>
                   </select>
               </div>
           </div>
       </div>
   </div>
</div>

<!-- Bảng xếp hạng thành tích học tập -->
<div class="row statistics-section">
    <div class="col-lg-12">
        <div class="table-container">
            <h5 class="chart-title">Bảng xếp hạng thành tích học tập</h5>
            <table class="stats-table">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã SV</th>
                        <th>Họ và tên</th>
                        <th>Điểm TB</th>
                        <th>Xếp loại</th>
                    </tr>
                </thead>
                <tbody id="studentsTableBody">
                    <c:choose>
                        <c:when test="${not empty scores}">
                            <c:forEach items="${scores}" var="s" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${s.student.id}</td>
                                    <td>${s.student.name}</td>
                                    <td><fmt:formatNumber value="${s.score_average}" maxFractionDigits="2" minFractionDigits="2" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${s.score_average >= 8.5}">
                                                Xuất sắc
                                            </c:when>
                                            <c:when test="${s.score_average >= 7}">
                                                Giỏi
                                            </c:when>
                                            <c:when test="${s.score_average >= 5.5}">
                                                Khá
                                            </c:when>
                                            <c:when test="${s.score_average >= 3.5}">
                                                Trung bình
                                            </c:when>
                                            <c:otherwise>
                                                Kém
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="text-center">Không có dữ liệu</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

                
            </div>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
    <script>
        function filterByCycle() {
            var cycleId = document.getElementById('semester').value;
            var url = new URL(window.location.href);
            if (cycleId !== 'all') {
                url.searchParams.set('cycleId', cycleId);
            } else {
                url.searchParams.delete('cycleId');
            }
            window.location.href = url.toString();
        }
    </script>
</body>
</html>