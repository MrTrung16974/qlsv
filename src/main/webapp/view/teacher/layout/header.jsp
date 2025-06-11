<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("name")==null){
		response.sendRedirect(request.getContextPath()+"/login");
	}
	String name = (String) session.getAttribute("name");
    String id = (String) session.getAttribute("id");
%>
<div class="header">
    <h1>HỆ THỐNG QUẢN LÝ SINH VIÊN – KHOA CNTT</h1>
    <nav class="navbar navbar-expand-lg navbar-light bg-light" style="padding: 0px;">
                   <div class="container-fluid" style="background-color: #2e5da1;">
                     <div class="collapse navbar-collapse" id="navbarSupportedContent">
                       <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                         <li class="nav-item dropdown">
                           <a class="nav-link dropdown-toggle text-white" style="padding: 0px;" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Xin chào : <%=name %>
                           </a>
                           <ul class="dropdown-menu" aria-labelledby="navbarDropdown" style="margin-top: 15px;padding: 0;">
                             <li><a class="dropdown-item custom-hover" onclick="return confirm('Bạn có chắc chắn muốn đăng xuất không?')" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                           </ul>
                         </li>
                       </ul>
                     </div>
                   </div>
                 </nav>
</div>

