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

@WebServlet("/admin/unlock-user")
public class UnlockUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDAO userDAO = new UserDAO();
        Teacher users = userDAO.findById(id);

        if (users != null) {
            if (users.getType() == 1) {
                userDAO.unLockUser(id);
                req.getSession().setAttribute("successMessage", "Mở khóa người dùng thành công!");
                resp.sendRedirect("/QLSV/admin/list-user");
            } else {
                req.getSession().setAttribute("errorMessage", "Loại người dùng không hợp lệ!");
                resp.sendRedirect("/QLSV/admin/list-user");
            }
        } else {
            Student student = userDAO.findStudentById(id);
            if (student != null) {
                userDAO.unLockStudent(student.getId());
                req.getSession().setAttribute("successMessage", "Mở khóa sinh viên thành công!");
                resp.sendRedirect("/QLSV/admin/sinhvien");
            } else {
                req.getSession().setAttribute("errorMessage", "Không tìm thấy người dùng hoặc sinh viên!");
                resp.sendRedirect("/QLSV/admin/list-user");
            }

        }
    }
}