package com.example.controller.admin;

import com.example.dao.CycleDAO;
import com.example.dao.SubjectDAO;
import com.example.dao.UserDAO;
import com.example.model.Cycle;
import com.example.model.Subject;
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

@WebServlet("/admin/update-monhoc")
public class UpdateSubjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Subject subject = new Subject();
        subject = new SubjectDAO().findById(id);
        List<Teacher> users = new ArrayList<>();
        users = new UserDAO().getAll();
        List<Cycle> cycles = new ArrayList<>();
        cycles = new CycleDAO().getAll();
        req.setAttribute("user", users);
        req.setAttribute("cycle", cycles);
        req.setAttribute("ss", subject);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/update-monhoc.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String ten = req.getParameter("ten");
        double hs1 = Double.parseDouble(req.getParameter("hs1"));
        double hs2 = Double.parseDouble(req.getParameter("hs2"));
        if (hs1 + hs2 > 1 || hs1 + hs2 < 1) {
            req.setAttribute("error", "Tổng 2 hệ số ko đc vướt quá hoặc thấp hơn 1 mời sửa lại");
            req.getRequestDispatcher("/view/admin/update-monhoc.jsp").forward(req, resp);
            return;
        }
        String teacherId = req.getParameter("teacher");
        String cycleId = req.getParameter("cycle");
        Subject subject = new Subject();
        subject.setId(id);
        subject.setName(ten);
        subject.setProcessCoefficient(hs1);
        subject.setExamCoefficient(hs2);
        subject.setLastmodified(new Date(System.currentTimeMillis()));
        subject.setDeleted(false);
        subject.setStatus(false);
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        subject.setTeacher(teacher);
        Cycle cycle = new Cycle();
        cycle.setId(cycleId);
        subject.setCycle(cycle);
        SubjectDAO subjectDAO = new SubjectDAO();
        try {
            subjectDAO.update(subject);
            req.getSession().setAttribute("successMessage", "Thay đổi thông tin thành công!");
            resp.sendRedirect("/qlsv/admin/mon-hoc");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi trong quá trình thêm mới môn học");
            req.getRequestDispatcher("/view/admin/update-monhoc.jsp").forward(req, resp);
        }
    }
}
