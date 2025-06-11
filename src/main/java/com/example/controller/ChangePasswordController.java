package com.example.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.LoginDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getSession().getAttribute("id").toString();
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        LoginDAO loginDAO = new LoginDAO();
//        Login login = loginDAO.getLoginByUserId(id);
//        BCrypt.Result result = BCrypt.verifyer().verify(currentPassword.toCharArray(), login.getPassword());
//
//        String userType = login.getUsers().getType();
//        String redirectPage = switch (userType) {
//            case "sinhvien" -> "/view/student/index.jsp";
//            case "giaovien" -> "/view/teacher/index.jsp";
//            default -> "/view/admin/profile.jsp";
//        };
//
//        if (!result.verified) {
//            req.setAttribute("error", "Mật khẩu hiện tại không đúng!");
//            req.getRequestDispatcher(redirectPage).forward(req, resp);
//            return;
//        }

//        if (!newPassword.equals(confirmPassword)) {
//            req.setAttribute("error", "Xác nhận mật khẩu không khớp!");
//            req.getRequestDispatcher(redirectPage).forward(req, resp);
//            return;
//        }
        String hashedNewPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
        loginDAO.updatePassByUserId(id, hashedNewPassword);
        resp.sendRedirect(req.getContextPath() + "/login?changeSuccess=true");
    }
}
