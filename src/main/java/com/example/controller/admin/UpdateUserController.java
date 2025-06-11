package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.UserDAO;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.util.StringUtil;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

@WebServlet("/admin/update-user")
public class UpdateUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UserDAO userDAO = new UserDAO();
        Teacher users = userDAO.findById(id);
        req.setAttribute("activePage", "giaovien");
        req.setAttribute("users", users);
        req.getRequestDispatcher("/view/admin/update-user.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("ten");
        String phone = req.getParameter("sdt");
        String email = req.getParameter("email");
        String dateOfBirth = req.getParameter("ngaysinh");

        Teacher user = new Teacher();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setDateOfBirth(Date.valueOf(dateOfBirth));
        user.setLastmodified(new java.sql.Date(System.currentTimeMillis()));
        UserDAO userDAO = new UserDAO();

        // Sinh mã người dùng
        if(Objects.isNull(user.getId()) || StringUtil.isBlank(user.getId())) {
            var userCode = userDAO.genUserCode("TEACHER");
            if (Objects.nonNull(userCode)) user.setId(userCode);
        }

        try {
            userDAO.updateUser(user);
            req.getSession().setAttribute("successMessage", "Sửa người dùng thành công!");
            resp.sendRedirect("/qlsv/admin/list-user");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Có lỗi xảy ra khi thêm người dùng.");
            req.getRequestDispatcher("/view/admin/update-user.jsp").forward(req, resp);
        }
    }
}
