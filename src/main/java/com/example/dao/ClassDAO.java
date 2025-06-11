package com.example.dao;

import com.example.model.Class;
import com.example.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO extends DBConnect {
    public List<Class> getClassByTeacher(String teacherId) {
        List<Class> classList = new ArrayList<>();
        String sql = "SELECT c.*, u.* " +
                "FROM CLASS c " +
                "JOIN TEACHER u ON c.TEACHER_ID = u.ID " +
                "WHERE c.TEACHER_ID = ? AND c.DELETED = 0";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, teacherId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Teacher teacher = new UserDAO().findById(rs.getString("TEACHER_ID"));

                Class clazz = new Class(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        teacher
                );

                classList.add(clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return classList;
    }

    public Class getClassByStudentId(String studentId) {
        String sql = "SELECT c.* " +
                "FROM CLASS c " +
                "JOIN CLASS_STUDENT cu ON c.ID = cu.CLASS_ID " +
                "JOIN STUDENT u ON cu.STUDENT_ID = u.ID " +
                "WHERE u.ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Class clazz = new Class(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                       null
                );
                return clazz;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public List<Class> getAll(){
        List<Class> classes = new ArrayList<>();
        String sql = "select * from CLASS where DELETED = 0";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String teacherId = rs.getString("TEACHER_ID");
                Teacher teacher = new UserDAO().findById(teacherId);
                Class clazz = new Class(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString(null),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        teacher
                );
                classes.add(clazz);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classes;
    }
    public List<Class> searchByName(String keyword) {
        List<Class> classList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM CLASS c " +
                "WHERE c.DELETED = 0 AND c.NAME LIKE ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Class cls = new Class();
                cls.setId(rs.getString("ID"));
                cls.setName(rs.getString("NAME"));
                cls.setDescription(rs.getString("DESCRIPTION"));
                cls.setCreatedAt(rs.getDate("CREATED_AT"));
                cls.setLastModified(rs.getDate("LASTMODIFIED"));
                cls.setDeleted(rs.getBoolean("DELETED"));
                cls.setStatus(rs.getBoolean("STATUS"));
                String teacherId = rs.getString("TEACHER_ID");
                Teacher teacher = new UserDAO().findById(teacherId);
                cls.setTeacher(teacher);
                classList.add(cls);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    public void insert(Class clazz) {
        String sql = "INSERT INTO CLASS (ID, NAME, DESCRIPTION, CREATED_AT, LASTMODIFIED, DELETED, STATUS, TEACHER_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clazz.getId());
            ps.setString(2, clazz.getName());
            ps.setString(3, clazz.getDescription());
            ps.setDate(4, new java.sql.Date(clazz.getCreatedAt().getTime()));
            ps.setDate(5, new java.sql.Date(clazz.getLastModified().getTime()));
            ps.setBoolean(6, clazz.isDeleted());
            ps.setBoolean(7, clazz.isStatus());
            if (clazz.getTeacher() != null && clazz.getTeacher().getId() != null) {
                ps.setString(8,clazz.getTeacher().getId());
            } else {
                ps.setNull(8, Types.VARCHAR);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Class getById(String id){
        String sql = "select * from CLASS where ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String teacherId = rs.getString("TEACHER_ID");
                Teacher teacher = new UserDAO().findById(teacherId);
                Class clazz = new Class(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        teacher);
               return clazz;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void update(Class clazz) {
        String sql = "UPDATE CLASS SET NAME = ?, DESCRIPTION = ?, LASTMODIFIED = ?, " +
                "DELETED = ?, STATUS = ?, TEACHER_ID = ? WHERE ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clazz.getName());
            ps.setString(2, clazz.getDescription());
            ps.setDate(3, new java.sql.Date(clazz.getLastModified().getTime()));
            ps.setBoolean(4, clazz.isDeleted());
            ps.setBoolean(5, clazz.isStatus());
            ps.setString(6, clazz.getTeacher().getId());
            ps.setString(7, clazz.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(String classId) {
        String sql = "UPDATE CLASS SET DELETED = 1 WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,classId );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void unDelete(String classId) {
        String sql = "UPDATE CLASS SET DELETED = 0 WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,classId );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void lock(String classId) {
        String sql = "UPDATE CLASS SET STATUS = 1 WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,classId );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void unLock(String classId) {
        String sql = "UPDATE CLASS SET STATUS = 0 WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,classId );
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int countClasses() {
        String sql = "SELECT COUNT(*) FROM CLASS WHERE DELETED = 0";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
