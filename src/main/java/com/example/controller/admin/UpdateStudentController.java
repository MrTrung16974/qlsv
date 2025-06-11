package com.example.controller.admin;

import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/update-sinhvien")
public class UpdateStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Student users = new UserDAO().findStudentById(id);
        req.setAttribute("activePage", "sinhvien");
        req.setAttribute("users", users);
        req.getRequestDispatcher("/view/admin/update_sinhvien.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("ten");
        String phone = req.getParameter("sdt");
        String email = req.getParameter("email");
        String address = req.getParameter("diachi");
        String dateOfBirth = req.getParameter("ngaysinh");
        String startTime = req.getParameter("starttime");
        String endTime = req.getParameter("endtime");
        Student user = new Student();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setStartYear(Date.valueOf(startTime));
        user.setEndYear(Date.valueOf(endTime));
        user.setDateOfBirth(Date.valueOf(dateOfBirth));
        user.setLastmodified(new java.sql.Date(System.currentTimeMillis()));
        UserDAO userDAO = new UserDAO();


        try {
            userDAO.updateStudent(user);
            req.getSession().setAttribute("successMessage", "Sửa người dùng thành công!");
            resp.sendRedirect("/qlsv/admin/sinhvien");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Có lỗi xảy ra khi thêm người dùng.");
            req.getRequestDispatcher("/view/admin/update-user.jsp").forward(req, resp);
        }
    }
}
