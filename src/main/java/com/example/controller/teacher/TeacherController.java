package com.example.controller.teacher;

import com.example.dao.UserDAO;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/teacher")
public class TeacherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String) req.getSession().getAttribute("id");
        Teacher teacher = new UserDAO().findById(id);
        req.setAttribute("t",teacher);
        req.setAttribute("activePage", "home");
        req.getRequestDispatcher("/view/teacher/index.jsp").forward(req,resp);
    }
}
