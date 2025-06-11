package com.example.controller.admin;

import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/sinhvien")
public class ListStudentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";

        int page = 1;
        int recordsPerPage = 5;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        UserDAO userDAO = new UserDAO();
        List<Student> usersList = userDAO.searchStudent(keyword, (page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = userDAO.countStudent(keyword);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        req.setAttribute("usersList", usersList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);
        req.setAttribute("activePage", "sinhvien");
        req.getRequestDispatcher("/view/admin/sinhvien.jsp").forward(req, resp);
    }
}
