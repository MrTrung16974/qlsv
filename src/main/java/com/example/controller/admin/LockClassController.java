package com.example.controller.admin;

import com.example.dao.ClassDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/lock-lophoc")
public class LockClassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ClassDAO classDAO = new ClassDAO();
        classDAO.lock(id);
        req.getSession().setAttribute("successMessage", "Khóa lớp thành công!");
        resp.sendRedirect("/QLSV/admin/lop-hoc");
    }
}
