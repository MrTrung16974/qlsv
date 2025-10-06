package com.example.controller.admin;

import com.example.dao.ScoreSubjectDAO;
import com.example.dao.SubjectDAO;
import com.example.model.ScoreSubject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/admin/add-score-time")
public class AddScoreTimeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssid = req.getParameter("id");
        req.setAttribute("sId",ssid);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/add-score-time.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subjectId = req.getParameter("id");
        String startDate = req.getParameter("nbd");
        String endDate = req.getParameter("nkt");
        try {
            new SubjectDAO().updateTimeBySubjectId(Date.valueOf(startDate),Date.valueOf(endDate),subjectId);
            req.getSession().setAttribute("successMessage", "Thêm mới thành công!");
            resp.sendRedirect("/QLSV/admin/score-time?id="+subjectId);
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Thêm không thành công!");
            req.getRequestDispatcher("/view/admin/add-score-time.jsp").forward(req,resp);
        }


    }
}
