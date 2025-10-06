package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.LoginDAO;
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
import java.util.Objects;
import java.util.UUID;

@WebServlet("/admin/add-sinhvien")
public class AddStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("activePage", "sinhvien");
        req.getRequestDispatcher("/view/admin/add-sinhvien.jsp").forward(req,resp);
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


        String hashedPassword = BCrypt.withDefaults().hashToString(12,dateOfBirth.toCharArray());
        Student user = new Student();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setDateOfBirth(Date.valueOf(dateOfBirth));
        user.setStartYear(Date.valueOf(startTime));
        user.setEndYear(Date.valueOf(endTime));
        user.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
        user.setLastmodified(new java.sql.Date(System.currentTimeMillis()));
        user.setDeleted(false);
        user.setStatus(false);
        user.setPassword(hashedPassword);

        UserDAO userDAO = new UserDAO();

        // Sinh mã người dùng
        var userCode = userDAO.genUserCode("USER");
        if(Objects.nonNull(userCode)) user.setId(userCode);
        try {
            userDAO.addStudent(user);

            req.getSession().setAttribute("successMessage", "Thêm mới người dùng thành công!");
            resp.sendRedirect("/QLSV/admin/sinhvien");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("reopenModal", true);
            req.setAttribute("error", "Có lỗi xảy ra khi thêm người dùng.");
            req.getRequestDispatcher("/view/admin/add-sinhvien.jsp").forward(req, resp);
        }
    }
}
