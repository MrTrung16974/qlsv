package com.example.controller.admin;

import com.example.dao.ClassDAO;
import com.example.dao.ClassUserDAO;
import com.example.dao.UserDAO;
import com.example.model.Class;
import com.example.model.Class_Student;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/lop-student")
public class ClassStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classId = req.getParameter("id");
        String searchTerm = req.getParameter("searchTerm");
        if (searchTerm == null) {
            searchTerm = "";
        }
        List<Class_Student> classUsers = new ClassUserDAO().getAllStudentByClassId(classId, searchTerm);
        List<Student> studentsNotInClass = new UserDAO().getAllStudentNotInClass(classId);
        Class aClass = new ClassDAO().getById(classId);
        req.setAttribute("clazz", aClass);
        req.setAttribute("user", studentsNotInClass);
        req.setAttribute("cl", classUsers);
        req.setAttribute("activePage", "lophoc");
        req.getRequestDispatcher("/view/admin/student-class.jsp").forward(req, resp);
    }
}
