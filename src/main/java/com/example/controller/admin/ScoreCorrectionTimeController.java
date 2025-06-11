package com.example.controller.admin;

import com.example.dao.ScoreSubjectDAO;
import com.example.dao.SubjectDAO;
import com.example.model.ScoreSubject;
import com.example.model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/score-time")
public class ScoreCorrectionTimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subjectId = req.getParameter("id");
        Subject subject = new SubjectDAO().findById(subjectId);
        req.setAttribute("ss",subject);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/score-time.jsp").forward(req,resp);
    }
}
