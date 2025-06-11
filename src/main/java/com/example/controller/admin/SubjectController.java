package com.example.controller.admin;

import com.example.dao.SubjectDAO;
import com.example.model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/mon-hoc")
public class SubjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjects;
        if (!keyword.isEmpty()) {
            subjects = subjectDAO.searchByName(keyword);
        } else {
            subjects = subjectDAO.getAll();
        }
        req.setAttribute("subject", subjects);
        req.setAttribute("keyword", keyword);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/subject.jsp").forward(req, resp);
    }
}
