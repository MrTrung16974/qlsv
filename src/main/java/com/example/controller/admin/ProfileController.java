package com.example.controller.admin;

import com.example.dao.UserDAO;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/profile")
public class ProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String) req.getSession().getAttribute("id");
        Teacher teacher = new UserDAO().findById(id);
        req.setAttribute("st",teacher);
        req.setAttribute("activePage", "hoso");
        req.getRequestDispatcher("/view/admin/profile.jsp").forward(req,resp);
    }
}
