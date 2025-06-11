package com.example.controller.admin;

import com.example.dao.ClassUserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/add-classstudent")
public class AddClassStudentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classId = req.getParameter("clazz");
        String[] studentIds = req.getParameterValues("students");
        List<Student> students = new ArrayList<>();
        for (String id : studentIds) {
            Student u = new Student();
            u.setId(id);
            students.add(u);
        }
        ClassUserDAO classUserDAO = new ClassUserDAO();
        try {
            classUserDAO.addStudentsToClass(classId, students);
            req.setAttribute("activePage", "lophoc");
            req.getSession().setAttribute("successMessage", "Thêm mới sinh viên thành công!");
            resp.sendRedirect("/qlsv/admin/lop-student?id="+classId);
        }catch (Exception e){
            e.printStackTrace();
            String errorMessage = "Thêm mới không thành công!";
            String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/admin/lop-student?id="+classId+"&reopenModal=true&error=" + encodedError);
            req.getRequestDispatcher("/view/admin/add-classstudent.jsp").forward(req,resp);
        }

    }
}
