package com.example.controller.admin;

import com.example.dao.ClassDAO;
import com.example.dao.UserDAO;
import com.example.model.Class;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/add-lophoc")
public class AddClassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> teachers = new ArrayList<>();
        teachers = new UserDAO().getAll();
        req.setAttribute("user",teachers);
        req.setAttribute("activePage", "lophoc");
        req.getRequestDispatcher("/view/admin/add-lophoc.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("ten");
        String teacherId = req.getParameter("teacher");
        Class aClass = new Class();
        aClass.setId(UUID.randomUUID().toString().substring(0,16));
        aClass.setName(name);
        aClass.setDeleted(false);
        aClass.setCreatedAt(new Date(System.currentTimeMillis()));
        aClass.setLastModified(new Date(System.currentTimeMillis()));
        aClass.setStatus(false);
        if (teacherId != null && !teacherId.trim().isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            aClass.setTeacher(teacher);
        }else {
            aClass.setTeacher(null);
        }
        try {
            ClassDAO classDAO = new ClassDAO();
            classDAO.insert(aClass);
            req.getSession().setAttribute("successMessage", "Thêm mới lớp học thành công!");
            resp.sendRedirect("/QLSV/admin/lop-hoc");
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Thêm mới không thành công hãy thêm lại");
            req.getRequestDispatcher("/view/admin/add-lophoc.jsp").forward(req,resp);
        }
  }
}
