package com.example.controller.admin;

import com.example.dao.ScoreSubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-score")
public class DeleteScoreStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");
        String ssId = req.getParameter("id");
        String classId = req.getParameter("classId");
        new ScoreSubjectDAO().deleteScoreSubjectById(ssId);
        req.getSession().setAttribute("successMessage", "xóa điểm thành công!");
        resp.sendRedirect("/QLSV/admin/diem-student?id="+classId+"&studentId="+studentId);
    }
}
