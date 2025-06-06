package com.example.controller.admin;

import com.example.dao.ScoreDAO;
import com.example.dao.ScoreSubjectDAO;
import com.example.dao.SubjectDAO;
import com.example.model.Score;
import com.example.model.ScoreSubject;
import com.example.model.Subject;
import com.example.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.UUID;

@WebServlet("/admin/add-score")
public class AddScoreController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");
        Users users = new Users();
        users.setId(studentId);
        Score score = new Score();
        score.setId(UUID.randomUUID().toString().substring(0,16));
        score.setCreateAt(new Date(System.currentTimeMillis()));
        score.setLastmodified(new Date(System.currentTimeMillis()));
        score.setDeleted(false);
        score.setUsers(users);
        ScoreDAO scoreDAO = new ScoreDAO();
        scoreDAO.add(score);

        String scoreLaborious = req.getParameter("chuyencan");
        String scoreCheck = req.getParameter("kiemtra");
        String scoreFinal = req.getParameter("thi");
        String subjectId = req.getParameter("subjectId");
        String classId = req.getParameter("classId");
        try {
            double laborious = Double.parseDouble(scoreLaborious);
            double check = Double.parseDouble(scoreCheck);
            double sFinal = Double.parseDouble(scoreFinal);
            Subject subject = new Subject();
            subject = new SubjectDAO().findById(subjectId);
            subject.setId(subjectId);
            ScoreSubject ss = new ScoreSubject();
            ss.setId(UUID.randomUUID().toString().substring(0,16));
            ss.setScoreLaborious(laborious);
            ss.setScoreCheck(check);
            ss.setScoreFinal(sFinal);
            ss.setScore_average((((laborious+check)/2)*subject.getProcessCoefficient())+(sFinal*subject.getExamCoefficient()));
            ss.setScore(score);
            ss.setSubject(subject);
            ScoreSubjectDAO subjectDAO = new ScoreSubjectDAO();
            subjectDAO.addScoreSubject(ss);
            req.getSession().setAttribute("successMessage", "Thêm mới điểm thành công!");
            resp.sendRedirect("/qlsv/admin/diem-student?id="+classId+"&studentId="+studentId);
        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = "Đã xảy ra lỗi khi thêm điểm mời thêm lại!";
            String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/qlsv/admin/diem-student?id="+classId+"$studentId="+studentId+"reopenModal=true&error=" + encodedError);
        }
    }
}
