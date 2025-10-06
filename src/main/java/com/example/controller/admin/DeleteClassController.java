package com.example.controller.admin;

import com.example.dao.ClassDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-lophoc")
public class DeleteClassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ClassDAO classDAO = new ClassDAO();
        classDAO.delete(id);
        req.getSession().setAttribute("successMessage", "Xóa lớp thành công!");
        resp.sendRedirect("/QLSV/admin/lop-hoc");
    }
}
