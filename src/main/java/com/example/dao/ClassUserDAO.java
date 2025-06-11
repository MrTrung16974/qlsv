package com.example.dao;

import com.example.model.Class;
import com.example.model.Class_Student;
import com.example.model.Student;
import com.example.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassUserDAO extends DBConnect{
    public List<Class_Student> getStudentsByTeacher(String teacherId, String classId) {
        List<Class_Student> list = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_STUDENT c " +
                "JOIN CLASS cu ON c.CLASS_ID = cu.ID " +
                "WHERE cu.TEACHER_ID = ? AND cu.ID = ? ";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, teacherId);
            stmt.setString(2,classId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new UserDAO().findStudentById(rs.getString("STUDENT_ID"));
                Class aClass = new ClassDAO().getById(rs.getString("CLASS_ID"));

                list.add(new Class_Student(student, aClass));
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students by teacher", e);
        }

        return list;
    }
    public List<Class_Student> getAllStudentByClassId(String classId, String searchTerm) {
        List<Class_Student> classUsers = new ArrayList<>();
        String sql = "SELECT * FROM CLASS_STUDENT cu " +
                "JOIN STUDENT u ON cu.STUDENT_ID = u.ID " +
                "WHERE cu.CLASS_ID = ? " +
                "AND (u.ID LIKE ? OR u.NAME LIKE ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, classId);
            ps.setString(2, "%" + searchTerm + "%");
            ps.setString(3, "%" + searchTerm + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = new UserDAO().findStudentById(rs.getString("STUDENT_ID"));
                Class clazz = new ClassDAO().getById(rs.getString("CLASS_ID"));
                Class_Student classUser = new Class_Student(student, clazz);
                classUsers.add(classUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classUsers;
    }
    public void addStudentsToClass(String classId, List<Student> students) {
        String sql = "INSERT INTO CLASS_STUDENT (ID,CLASS_ID, STUDENT_ID,CREATE_AT,LASTMODIFIED) VALUES (?, ?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Student student : students) {
                Class_Student classStudent = new Class_Student();
                ps.setString(1,classStudent.getId());
                ps.setString(2, classId);
                ps.setString(3, student.getId());
                ps.setDate(4,classStudent.getCreateAt());
                ps.setDate(5,classStudent.getLassmodified());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding students to class", e);
        }
    }
    public void delete(String classId, String studentId) {
        String sql = "DELETE FROM CLASS_STUDENT WHERE CLASS_ID = ? AND STUDENT_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, classId);
            ps.setString(2, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student from class", e);
        }
    }
}
