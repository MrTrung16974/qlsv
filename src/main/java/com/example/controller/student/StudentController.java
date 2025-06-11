package com.example.controller.student;

import com.example.dao.ClassDAO;
import com.example.dao.UserDAO;
import com.example.model.Class;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/student")
public class StudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String) req.getSession().getAttribute("id");
        Student student = new UserDAO().findStudentById(id);
        Class aClass = new ClassDAO().getClassByStudentId(id);
        req.setAttribute("cl",aClass);
        req.setAttribute("st",student);
        req.setAttribute("activePage", "home");
        req.getRequestDispatcher("/view/student/index.jsp").forward(req,resp);
    }
}
