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
import java.util.UUID;

@WebServlet("/admin/add-monhoc")
public class AddSubjectController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> users = new ArrayList<>();
        users = new UserDAO().getAll();
        List<Cycle> cycles = new ArrayList<>();
        cycles = new CycleDAO().getAll();
        req.setAttribute("user",users);
        req.setAttribute("cycle",cycles);
        req.setAttribute("activePage", "monhoc");
        req.getRequestDispatcher("/view/admin/add-monhoc.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ten = req.getParameter("ten");
        double hs1 = Double.parseDouble(req.getParameter("hs1"));
        double hs2 = Double.parseDouble(req.getParameter("hs2"));
        if (hs1+hs2 > 1 || hs1+hs2 < 1){
            req.setAttribute("error","Tổng 2 hệ số ko đc vướt quá hoặc thấp hơn 1 mời nhập lại");
            req.getRequestDispatcher("/view/admin/add-monhoc.jsp").forward(req,resp);
            return;
        }
        String teacherId = req.getParameter("teacher");
        String cycleId = req.getParameter("cycle");
        Subject subject = new Subject();
        subject.setId(UUID.randomUUID().toString().substring(0,16));
        subject.setName(ten);
        subject.setProcessCoefficient(hs1);
        subject.setExamCoefficient(hs2);
        subject.setCreateAt(new Date(System.currentTimeMillis()));
        subject.setLastmodified(new Date(System.currentTimeMillis()));
        subject.setDeleted(false);
        subject.setStatus(false);
        if (teacherId != null && !teacherId.trim().isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            subject.setTeacher(teacher);
        }else {
            subject.setTeacher(null);
        }
        if (cycleId != null && !cycleId.trim().isEmpty()) {
            Cycle cycle = new Cycle();
            cycle.setId(cycleId);
            subject.setCycle(cycle);
        } else {
            subject.setCycle(null);
        }
        SubjectDAO subjectDAO = new SubjectDAO();
        try {
            subjectDAO.add(subject);
            req.getSession().setAttribute("successMessage", "Thêm mới môn học thành công!");
            resp.sendRedirect("/QLSV/admin/mon-hoc");
        }catch (Exception e){
            e.printStackTrace();
            req.setAttribute("error","Đã xảy ra lỗi trong quá trình thêm mới môn học");
            req.getRequestDispatcher("/view/admin/add-monhoc.jsp").forward(req,resp);
        }
    }
}
