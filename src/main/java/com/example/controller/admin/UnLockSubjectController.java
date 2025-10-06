package com.example.controller.admin;

import com.example.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/unlock-subject")
public class UnLockSubjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        SubjectDAO subjectDAO = new SubjectDAO();
        subjectDAO.unLockSubject(id);
        req.getSession().setAttribute("successMessage", "Mở khóa môn học thành công!");
        resp.sendRedirect("/QLSV/admin/mon-hoc");
    }
}
