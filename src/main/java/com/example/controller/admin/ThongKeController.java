package com.example.controller.admin;

import com.example.dao.ClassDAO;
import com.example.dao.CycleDAO;
import com.example.dao.ScoreDAO;
import com.example.dao.UserDAO;
import com.example.model.Cycle;
import com.example.model.ScoreSubject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/thong-ke")
public class ThongKeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        int countTeacher = userDAO.countTeachers();
        int countStudent = userDAO.countStudents();
        int countClass = new ClassDAO().countClasses();

        String cycleId = req.getParameter("cycleId");
        if (cycleId == null) {
            cycleId = "all";
        }


        List<ScoreSubject> scores = new ScoreDAO().getTopStudentsByCycle(cycleId);

        req.setAttribute("teacher", countTeacher);
        req.setAttribute("student", countStudent);
        req.setAttribute("clazz", countClass);
        req.setAttribute("scores", scores);


        List<Cycle> cycles = new CycleDAO().getAll();
        req.setAttribute("cycles", cycles);

        req.setAttribute("activePage", "thongke");
        req.getRequestDispatcher("/view/admin/thongke.jsp").forward(req, resp);
    }
}
