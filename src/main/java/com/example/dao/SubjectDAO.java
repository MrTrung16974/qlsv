package com.example.dao;

import com.example.model.Cycle;
import com.example.model.Subject;
import com.example.model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO extends DBConnect{
    CycleDAO cycleDAO = new CycleDAO();
    UserDAO userDAO = new UserDAO();
    public List<Subject> searchByName(String keyword) {
        List<Subject> subjectList = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT WHERE DELETED = 0 AND NAME LIKE ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cycleId = rs.getString("CYCLE_ID");
                Cycle cycle = cycleDAO.findById(cycleId);
                String teacherId = rs.getString("TEACHER_ID");
                Teacher users = userDAO.findById(teacherId);

                Subject subject = new Subject(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDouble("PROCESS_COEFFICIENT"),
                        rs.getDouble("EXAM_COEFFICIENT"),
                        rs.getDate("CREATE_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        cycle,
                        users
                );
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
    public List<Subject> getAll() {
        List<Subject> subjectList = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT where DELETED = 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cycleId = rs.getString("CYCLE_ID");
                Cycle cycle = cycleDAO.findById(cycleId);
                String teacherId = rs.getString("TEACHER_ID");
                Teacher users = userDAO.findById(teacherId);

                Subject subject = new Subject(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDouble("PROCESS_COEFFICIENT"),
                        rs.getDouble("EXAM_COEFFICIENT"),
                        rs.getDate("CREATE_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        cycle,
                        users
                );
                subjectList.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
    public List<Subject> getSubjectsByTeacherId(String teacherId) {
        List<Subject> subjectList = new ArrayList<>();
        String sql = "SELECT * FROM SUBJECT WHERE TEACHER_ID = ? AND DELETED = 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cycleId = rs.getString("CYCLE_ID");
                Cycle cycle = cycleDAO.findById(cycleId);
                Subject subject = new Subject(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("CREATE_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        cycle
                );
                subjectList.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
    public Subject findById(String id) {
        String sql = "SELECT * FROM SUBJECT WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String cycleId = rs.getString("CYCLE_ID");
                Cycle cycle = cycleDAO.findById(cycleId);
                String teacherId = rs.getString("TEACHER_ID");
                Teacher users = userDAO.findById(teacherId);

                Subject subject = new Subject(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("DESCRIPTION"),
                        rs.getDouble("PROCESS_COEFFICIENT"),
                        rs.getDouble("EXAM_COEFFICIENT"),
                        rs.getDate("CREATE_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        rs.getBoolean("STATUS"),
                        cycle,
                        users,
                        rs.getDate("START_DATE"),
                        rs.getDate("END_DATE")
                );
                return subject;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void add(Subject subject) {
        String sql = "INSERT INTO SUBJECT (ID, NAME, DESCRIPTION,PROCESS_COEFFICIENT,EXAM_COEFFICIENT, CREATE_AT, LASTMODIFIED, DELETED, STATUS, CYCLE_ID,TEACHER_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subject.getId());
            ps.setString(2, subject.getName());
            ps.setString(3, subject.getDescription());
            ps.setDouble(4,subject.getProcessCoefficient());
            ps.setDouble(5,subject.getExamCoefficient());
            ps.setDate(6, new java.sql.Date(subject.getCreateAt().getTime()));
            ps.setDate(7, new java.sql.Date(subject.getLastmodified().getTime()));
            ps.setBoolean(8, subject.isDeleted());
            ps.setBoolean(9, subject.isStatus());
            if (subject.getCycle() != null && subject.getCycle().getId() != null) {
                ps.setString(10, subject.getCycle().getId());
            } else {
                ps.setNull(10, Types.VARCHAR);
            }
            if (subject.getTeacher() != null && subject.getTeacher().getId() != null) {
                ps.setString(11,subject.getTeacher().getId());
            } else {
                ps.setNull(11, Types.VARCHAR);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Subject subject) {
        String sql = "UPDATE SUBJECT SET NAME = ?, PROCESS_COEFFICIENT = ?, EXAM_COEFFICIENT=?, LASTMODIFIED = ?, DELETED = ?, STATUS = ?, CYCLE_ID = ?, TEACHER_ID = ? WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subject.getName());
            ps.setDouble(2,subject.getProcessCoefficient());
            ps.setDouble(3,subject.getExamCoefficient());
            ps.setDate(4, new java.sql.Date(subject.getLastmodified().getTime()));
            ps.setBoolean(5, subject.isDeleted());
            ps.setBoolean(6, subject.isStatus());
            ps.setString(7,subject.getCycle().getId());
            ps.setString(8,subject.getTeacher().getId());
            ps.setString(9, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "UPDATE SUBJECT SET DELETED = 1 WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void lockSubject(String subjectId) {
        String sql = "UPDATE SUBJECT SET STATUS = 1 WHERE ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void unLockSubject(String subjectId) {
        String sql = "UPDATE SUBJECT SET STATUS = 0 WHERE ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTimeBySubjectId(Date startDate, Date endDate , String subjectId) {
        String sql = "UPDATE SUBJECT SET START_DATE = ?, END_DATE = ? WHERE ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            ps.setString(3, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTimeBySubjectId(String subjectId) {
        String sql = "UPDATE SUBJECT SET START_DATE = NULL, END_DATE = NULL WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
