package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.LoginDAO;
import com.example.dao.UserDAO;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@WebServlet(urlPatterns = {"/admin/list-user"})
public class TeacherController extends HttpServlet {
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
        List<Teacher> usersList = userDAO.searchUsers(keyword, (page - 1) * recordsPerPage, recordsPerPage);
        int totalRecords = userDAO.countUsers(keyword);
        int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

        req.setAttribute("usersList", usersList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);
        req.setAttribute("activePage", "giaovien");
        req.getRequestDispatcher("/view/admin/index.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("ten");
        String phone = req.getParameter("sdt");
        String email = req.getParameter("email");
        String dateOfBirth = req.getParameter("ngaysinh");

        UserDAO userDAO = new UserDAO();
        Teacher check = userDAO.findById(id);
        if (check != null){
            String errorMessage = "Bạn đã nhập trùng mã giáo viên đã có trong hệ thống. Mời nhập lại!";
            String encodedError = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/admin/list-user?reopenModal=true&error=" + encodedError);
            return;
        }
        String hashedPassword = BCrypt.withDefaults().hashToString(12, dateOfBirth.toCharArray());
        Teacher user = new Teacher();
        user.setId(id);
        // Sinh mã người dùng
        var userCode = userDAO.genUserCode("TEACHER");
        if(Objects.nonNull(userCode)) user.setId(userCode);

        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setDateOfBirth(Date.valueOf(dateOfBirth));
        user.setType(1);
        user.setCreateAt(new java.sql.Date(System.currentTimeMillis()));
        user.setLastmodified(new java.sql.Date(System.currentTimeMillis()));
        user.setDeleted(false);
        user.setStatus(false);
        user.setPassword(hashedPassword);

        try {
            userDAO.addUser(user);
            req.getSession().setAttribute("successMessage", "Thêm mới người dùng thành công!");
            resp.sendRedirect(req.getContextPath() + "/admin/list-user");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("reopenModal", true);
            req.setAttribute("error", "Có lỗi xảy ra khi thêm người dùng.");
            req.getRequestDispatcher("/view/admin/index.jsp").forward(req, resp);
        }
    }

}
