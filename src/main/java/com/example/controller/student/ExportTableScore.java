package com.example.controller.student;

import com.example.dao.ScoreSubjectDAO;
import com.example.model.ScoreSubject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/student/export-score")
public class ExportTableScore extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = (String) req.getSession().getAttribute("id");
        List<ScoreSubject> scoreSubjects = new ScoreSubjectDAO().getScoreSubjectByStudentId(id);
        if ("export-excel".equals(req.getParameter("action"))) {
            exportToExcel(scoreSubjects, resp);
        }
    }
    private void exportToExcel(List<ScoreSubject> scoreSubjects, HttpServletResponse resp) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Bảng điểm");
        Row hRow = sheet.createRow(0);
        hRow.createCell(0).setCellValue("STT");
        hRow.createCell(1).setCellValue("Tên học phần");
        hRow.createCell(2).setCellValue("Học kỳ");
        hRow.createCell(3).setCellValue("Điểm quá trình");
        hRow.createCell(4).setCellValue("Điểm cuối kỳ");
        hRow.createCell(5).setCellValue("Điểm tổng kết");
        int stt = 1;
        for (ScoreSubject ss : scoreSubjects) {
            Row row = sheet.createRow(stt++);
            row.createCell(0).setCellValue(stt - 1);
            row.createCell(1).setCellValue(ss.getSubject().getName());
            row.createCell(2).setCellValue(ss.getSubject().getCycle().getName());
            row.createCell(3).setCellValue(ss.getScoreProcess());
            row.createCell(4).setCellValue(ss.getScoreFinal());
            row.createCell(5).setCellValue(ss.getScore_average());
        }
        // Thiết lập kiểu trả về cho response
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=bang_diem.xlsx");
        OutputStream out = resp.getOutputStream();
        workbook.write(out);
        out.close();
    }

}
