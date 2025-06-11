package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.LoginDAO;
import com.example.dao.UserDAO;
import com.example.model.Student;
import com.example.model.Teacher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;


@WebServlet("/admin/uploads")
@MultipartConfig
public class ImportExcelController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        int recordsAdded = 0;
        StringBuilder errors = new StringBuilder();

        try (InputStream fileContent = filePart.getInputStream();
             Workbook workbook = new XSSFWorkbook(fileContent)) {

            UserDAO userDAO = new UserDAO();
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                try {
                    Student user = new Student();
                    user.setId(getCellStringValue(row.getCell(0)));

                    // Sinh mã người dùng
                    if(Objects.isNull(user.getId()) || StringUtil.isBlank(user.getId())) {
                        var userCode = userDAO.genUserCode("USER");
                        if (Objects.nonNull(userCode)) user.setId(userCode);
                    }

                    user.setName(getCellStringValue(row.getCell(1)));
                    user.setAddress(getCellStringValue(row.getCell(2)));
                    user.setPhone(getCellStringValue(row.getCell(3)));
                    user.setEmail(getCellStringValue(row.getCell(4)));
                    Date defaultDate = Date.valueOf("2000-01-01");
                    Date dateOfBirth = getCellDateValue(row.getCell(5));
                    if (dateOfBirth == null) {
                        dateOfBirth = defaultDate;
                        System.out.println("Warning: Using default birth date for user " + user.getId());
                    }
                    user.setDateOfBirth(dateOfBirth);

                    Date startTime = getCellDateValue(row.getCell(6));
                    if (startTime == null) startTime = defaultDate;
                    user.setStartYear(startTime);

                    Date endTime = getCellDateValue(row.getCell(7));
                    if (endTime == null) endTime = defaultDate;
                    user.setEndYear(endTime);

                    Date currentDate = new Date(System.currentTimeMillis());
                    user.setCreateAt(currentDate);
                    user.setLastmodified(currentDate);

                    user.setDeleted(false);
                    user.setStatus(false);

                    String hashedPassword = BCrypt.withDefaults().hashToString(12, dateOfBirth.toString().toCharArray());
                    user.setPassword(hashedPassword);

                    userDAO.addStudent(user);
                    recordsAdded++;

                } catch (Exception e) {
                    String error = "Error processing row " + row.getRowNum() + ": " + e.getMessage();
                    System.err.println(error);
                    errors.append(error).append("\n");
                    e.printStackTrace();
                }
            }

            if (recordsAdded > 0) {
                String message = "Thêm mới " + recordsAdded + " người dùng thành công!";
                if (errors.length() > 0) {
                    message += " Một số hàng không được xử lý do lỗi.";
                }
                req.getSession().setAttribute("successMessage", message);
            } else {
                req.getSession().setAttribute("errorMessage", "Không có người dùng nào được thêm. Lỗi: " + errors.toString());
            }
            resp.sendRedirect(req.getContextPath() + "/admin/list-user");

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("errorMessage", "Lỗi khi import file: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/admin/list-user");
        }

    }
    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        return dateFormat.format(cell.getDateCellValue());
                    }
                    double numValue = cell.getNumericCellValue();
                    if (numValue == Math.floor(numValue)) {
                        return String.valueOf((int) numValue);
                    }
                    return String.valueOf(numValue);
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    try {
                        return String.valueOf(cell.getStringCellValue());
                    } catch (Exception e) {
                        try {
                            return String.valueOf(cell.getNumericCellValue());
                        } catch (Exception ex) {
                            return "";
                        }
                    }
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
    private java.sql.Date getCellDateValue(Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new java.sql.Date(cell.getDateCellValue().getTime());
                    }
                    return null;
                case STRING:
                    String dateStr = cell.getStringCellValue().trim();
                    if (dateStr.isEmpty()) return null;
                    String[] dateFormats = {
                            "yyyy-MM-dd", "MM/dd/yyyy", "dd/MM/yyyy",
                            "yyyy/MM/dd", "dd-MM-yyyy", "MM-dd-yyyy"
                    };
                    for (String format : dateFormats) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                            dateFormat.setLenient(false);
                            java.util.Date parsedDate = dateFormat.parse(dateStr);
                            return new java.sql.Date(parsedDate.getTime());
                        } catch (Exception e) {

                        }
                    }
                    return null;
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}