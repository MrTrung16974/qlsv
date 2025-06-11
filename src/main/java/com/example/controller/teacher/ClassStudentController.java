package com.example.controller.teacher;

import com.example.dao.ClassUserDAO;
import com.example.model.Class_Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/sinhvien-lop")
public class ClassStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String) req.getSession().getAttribute("id");
        String classId = req.getParameter("classId");
        List<Class_Student> classUsers = new ClassUserDAO().getStudentsByTeacher(id,classId);
        req.setAttribute("c",classUsers);
        req.setAttribute("activePage", "lophoc");
        req.getRequestDispatcher("/view/teacher/class-sinhvien.jsp").forward(req,resp);
    }
}
