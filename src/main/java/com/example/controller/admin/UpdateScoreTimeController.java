package com.example.controller.admin;


import com.example.dao.SubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/update-score-time")
public class UpdateScoreTimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subjectId = req.getParameter("id");
        String startDate = req.getParameter("nbd");
        String endDate = req.getParameter("nkt");
        req.setAttribute("sId",subjectId);
        req.setAttribute("nbd",startDate);
        req.setAttribute("nkt",endDate);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/update-score-time.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subjectId = req.getParameter("id");
        String startDate = req.getParameter("nbd");
        String endDate = req.getParameter("nkt");
        try {
            new SubjectDAO().updateTimeBySubjectId(Date.valueOf(startDate),Date.valueOf(endDate),subjectId);
            req.getSession().setAttribute("successMessage", "Thay đổi thành công!");
            resp.sendRedirect("/QLSV/admin/score-time?id="+subjectId);
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Thay đổi không thành công!");
            req.getRequestDispatcher("/view/admin/update-score-time.jsp").forward(req,resp);
        }
    }
}
