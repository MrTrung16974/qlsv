package com.example.controller;

import com.example.dao.LoginDAO;
import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        HttpSession session = req.getSession();
        Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
        if (failedAttempts == null) failedAttempts = 0;

        UserDAO userDAO = new UserDAO();
        LoginDAO loginDAO = new LoginDAO();

        Teacher userFromDb = userDAO.findById(username);
        Student student = userDAO.findStudentById(username);

        if (userFromDb == null && student == null) {
            req.setAttribute("error", "Tài khoản không tồn tại.");
            req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
            return;
        }
        if (userFromDb != null) {
            if (userFromDb.isDeleted()) {
                req.setAttribute("error", "Tài khoản của bạn đã bị xóa.");
                req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
                return;
            }

            if (userFromDb.isStatus()==true) {
                req.setAttribute("error", "Tài khoản của bạn đã bị khóa.");
                req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
                return;
            }

            Teacher userLogin = loginDAO.login(username, password);
            if (userLogin != null) {
                session.removeAttribute("failedAttempts");
                session.setAttribute("id", userLogin.getId());
                session.setAttribute("name", userLogin.getName());
                session.setAttribute("type", userLogin.getType());

                if (userLogin.getType() == 0) {
                    resp.sendRedirect("/QLSV/admin");
                } else if (userLogin.getType() == 1) {
                    resp.sendRedirect("/QLSV/teacher");
                }
                return;
            }
        }
        if (student != null) {
            if (student.isDeleted()) {
                req.setAttribute("error", "Tài khoản của bạn đã bị xóa.");
                req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
                return;
            }

            if (student.isStatus()) {
                req.setAttribute("error", "Tài khoản của bạn đã bị khóa.");
                req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
                return;
            }

            Student studentLogin = loginDAO.loginStudent(username, password);
            if (studentLogin != null) {
                session.removeAttribute("failedAttempts");
                session.setAttribute("id", studentLogin.getId());
                session.setAttribute("name", studentLogin.getName());
                resp.sendRedirect("/QLSV/student");
                return;
            }
        }

        failedAttempts++;
        session.setAttribute("failedAttempts", failedAttempts);

        if (failedAttempts >= 3 ) {
            if (userFromDb != null) {
                userDAO.lockUser(userFromDb.getId());
                req.setAttribute("error", "Tài khoản của bạn đã bị khóa do nhập sai quá 3 lần.");
            } else if (student != null) {
                userDAO.lockStudent(student.getId());
                req.setAttribute("error", "Tài khoản của bạn đã bị khóa do nhập sai quá 3 lần.");
            }
        } else {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
        }

        req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
    }
}
