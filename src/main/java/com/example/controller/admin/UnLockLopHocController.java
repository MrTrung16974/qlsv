package com.example.controller.admin;

import com.example.dao.ClassDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/unlock-lophoc")
public class UnLockLopHocController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        ClassDAO classDAO = new ClassDAO();
        classDAO.unLock(id);
        req.getSession().setAttribute("successMessage", "Mở khóa lớp học thành công!");
        resp.sendRedirect("/QLSV/admin/lop-hoc");
    }
}
