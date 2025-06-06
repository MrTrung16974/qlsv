package com.example.controller.admin;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.dao.DBConnect;
import com.example.dao.LoginDAO;
import com.example.dao.UserDAO;
import com.example.model.Login;
import com.example.model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
                // Skip header row
                if (row.getRowNum() == 0) continue;

                try {
                    // Create new user
                    Users user = new Users();
                    user.setId(getCellStringValue(row.getCell(0)));
                    user.setName(getCellStringValue(row.getCell(1)));
                    user.setAddress(getCellStringValue(row.getCell(2)));
                    user.setPhone(getCellStringValue(row.getCell(3)));
                    user.setEmail(getCellStringValue(row.getCell(4)));

                    // Default date if parsing fails - January 1, 2000
                    Date defaultDate = Date.valueOf("2000-01-01");

                    // Try to parse dates, use default if parsing fails
                    Date dateOfBirth = getCellDateValue(row.getCell(5));
                    if (dateOfBirth == null) {
                        dateOfBirth = defaultDate;
                        System.out.println("Warning: Using default birth date for user " + user.getId());
                    }
                    user.setDateOfBirth(dateOfBirth);

                    user.setType(getCellStringValue(row.getCell(6)));
                    user.setTypePosition(getCellStringValue(row.getCell(7)));

                    Date startTime = getCellDateValue(row.getCell(8));
                    if (startTime == null) startTime = defaultDate;
                    user.setStartTime(startTime);

                    Date endTime = getCellDateValue(row.getCell(9));
                    if (endTime == null) endTime = defaultDate;
                    user.setEndTime(endTime);

                    // Set current timestamp for creation and modification
                    Date currentDate = new Date(System.currentTimeMillis());
                    user.setCreateAt(currentDate);
                    user.setLastmodified(currentDate);

                    // Set default values
                    user.setDeleted(false);
                    user.setLockStatus(false);

                    // Handle password creation
                    String hashedPassword = BCrypt.withDefaults().hashToString(12, dateOfBirth.toString().toCharArray());

                    // Create Login object
                    Login login = new Login();
                    login.setId(UUID.randomUUID().toString().substring(0, 16));
                    login.setUsername(getCellStringValue(row.getCell(0))); // Using ID as username
                    login.setPassword(hashedPassword);
                    login.setDeleted(false);
                    login.setUsers(user);

                    // Add user and login to the database
                    userDAO.addUser(user);
                    new LoginDAO().addLogin(login);
                    recordsAdded++;

                } catch (Exception e) {
                    String error = "Error processing row " + row.getRowNum() + ": " + e.getMessage();
                    System.err.println(error);
                    errors.append(error).append("\n");
                    e.printStackTrace();
                    // Continue with next row instead of failing the entire import
                }
            }

            // Set message based on results
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