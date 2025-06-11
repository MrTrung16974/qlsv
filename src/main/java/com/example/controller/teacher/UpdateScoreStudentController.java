package com.example.controller.teacher;

import com.example.dao.ScoreDAO;
import com.example.dao.ScoreSubjectDAO;
import com.example.dao.SubjectDAO;
import com.example.dao.UserDAO;
import com.example.model.ScoreSubject;
import com.example.model.Student;
import com.example.model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/teacher/update-score")
public class UpdateScoreStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("scoreSubjectId");
        String studentId = req.getParameter("studentId");
        ScoreSubjectDAO scoreSubjectDAO = new ScoreSubjectDAO();
        ScoreSubject scoreSubject = scoreSubjectDAO.findById(id);
        String teacherId = (String) req.getSession().getAttribute("id");
        req.setAttribute("ss",scoreSubject);
        req.setAttribute("studentId",studentId);
        req.setAttribute("activePage", "lophoc");
        System.out.println("..."+id);
        req.getRequestDispatcher("/view/teacher/update-score.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("ssid");
        String scoreLaborious = req.getParameter("chuyencan");
        String scoreFinal = req.getParameter("thi");
        String studentId = req.getParameter("scoreId");
        String subjectId = req.getParameter("sid");
        try {
            double laborious = Double.parseDouble(scoreLaborious);
            double sFinal = Double.parseDouble(scoreFinal);
            Subject subject  = new SubjectDAO().findById(subjectId);
            Student student = new UserDAO().findStudentById(studentId);
            ScoreSubject ss = new ScoreSubject();
            ss.setId(id);
            ss.setScoreProcess(laborious);
            ss.setScoreFinal(sFinal);
            double diem1 = ((laborious*subject.getProcessCoefficient())+(sFinal*subject.getExamCoefficient()));
            ss.setScore_average(diem1);
            ss.setLassmodified(new Date(System.currentTimeMillis()));
            ss.setStudent(student);
            ss.setSubject(subject);
            ScoreSubjectDAO subjectDAO = new ScoreSubjectDAO();
            subjectDAO.updateScoreSubject(ss);
            req.getSession().setAttribute("successMessage", "Sửa điểm thành công!");
            resp.sendRedirect("/qlsv/teacher/diem-sinhvien?studentId="+ss.getStudent().getId());
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Đã xảy ra lỗi khi sửa điểm mời sửa lại");
            req.getRequestDispatcher("/view/teacher/update-score.jsp").forward(req,resp);
        }
    }
}
