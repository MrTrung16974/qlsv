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

@WebServlet("/admin/update-lophoc")
public class UpdateClassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Class aClass = new ClassDAO().getById(id);
        List<Teacher> teacher = new ArrayList<>();
        teacher = new UserDAO().getAll();
        req.setAttribute("user",teacher);
        req.setAttribute("cl",aClass);
        req.setAttribute("activePage", "lophoc");
        req.getRequestDispatcher("/view/admin/update-lophoc.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("ten");
        String teacherId = req.getParameter("teacher");
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        Class aClass = new Class();
        aClass.setId(id);
        aClass.setName(name);
        aClass.setDeleted(false);
        aClass.setLastModified(new Date(System.currentTimeMillis()));
        aClass.setStatus(false);
        aClass.setTeacher(teacher);
        try {
            ClassDAO classDAO = new ClassDAO();
            classDAO.update(aClass);
            req.getSession().setAttribute("successMessage", "Thay đổi thông tin thành công!");
            resp.sendRedirect("/qlsv/admin/lop-hoc");
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Thêm mới không thành công hãy thêm lại");
            req.getRequestDispatcher("/view/admin/update-lophoc.jsp").forward(req,resp);
        }
    }
}
