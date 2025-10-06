package com.example.controller.admin;

import com.example.dao.ScoreSubjectDAO;
import com.example.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-score-time")
public class DeleteScoreTimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        new SubjectDAO().deleteTimeBySubjectId(id);
        req.getSession().setAttribute("successMessage", "Xóa thời gian thành công!");
        resp.sendRedirect("/QLSV/admin/score-time?id="+id);
    }
}
