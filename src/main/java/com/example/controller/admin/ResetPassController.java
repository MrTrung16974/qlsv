package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.LoginDAO;
import com.example.dao.MailDAO;
import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/admin/reset-pass")
public class ResetPassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDAO userDAO = new UserDAO();
        Teacher teacher = userDAO.findById(id);

        try {
            String plainPass;
            String to;
            if (teacher != null) {
                plainPass = new SimpleDateFormat("yyyy-MM-dd").format(teacher.getDateOfBirth());
                to = teacher.getEmail();
                String hashedPass = BCrypt.withDefaults().hashToString(12, plainPass.toCharArray());
                new LoginDAO().updatePassByUserId(id, hashedPass);
                String subject = "Khôi phục mật khẩu";
                String content = "Mật khẩu mới của bạn là: " + plainPass;
                MailDAO.sendEmail(to, subject, content);
                req.getSession().setAttribute("successMessage", "Mật khẩu mới đã được gửi đến email của " + id);
                resp.sendRedirect("/QLSV/admin/list-user");

            } else {
                Student student = userDAO.findStudentById(id);
                if (student != null) {
                    plainPass = new SimpleDateFormat("yyyy-MM-dd").format(student.getDateOfBirth());
                    to = student.getEmail();

                    String hashedPass = BCrypt.withDefaults().hashToString(12, plainPass.toCharArray());
                    new LoginDAO().updatePassByStudentId(id, hashedPass);

                    String subject = "Khôi phục mật khẩu";
                    String content = "Mật khẩu mới của bạn là: " + plainPass;
                    MailDAO.sendEmail(to, subject, content);

                    req.getSession().setAttribute("successMessage", "Mật khẩu mới đã được gửi đến email của " + id);
                    resp.sendRedirect("/QLSV/admin/sinhvien");

                } else {
                    req.getSession().setAttribute("errorMessage", "Không tìm thấy người dùng.");
                    resp.sendRedirect("/QLSV/admin/list-user");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("errorMessage", "Không thể khôi phục mật khẩu cho tài khoản " + id);
            resp.sendRedirect("/QLSV/admin/list-user");
        }
    }
}
