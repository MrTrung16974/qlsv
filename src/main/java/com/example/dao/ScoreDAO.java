package com.example.dao;

import com.example.model.ScoreSubject;
import com.example.model.Student;
import com.example.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDAO extends DBConnect{

    public double getScoreByCycleId(String studentId, String cycleId) {
        String sql = "SELECT AVG(ss.SCORE_AVERAGE) AS average_score " +
                "FROM SCORE_SUBJECT ss " +
                "JOIN SUBJECT sub ON ss.SUBJECT_ID = sub.ID " +
                "JOIN CYCLE c ON sub.CYCLE_ID = c.ID " +
                "JOIN STUDENT u ON ss.STUDENT_ID = u.ID " +
                "WHERE c.ID = ? AND u.ID = ? AND sub.DELETED = 0 " +
                "GROUP BY u.ID, c.ID";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cycleId);
            ps.setString(2, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("average_score");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }
    public double getAverageScoreAllCourse(String studentId) {
        String sql = "SELECT AVG(SCORE_AVERAGE) AS average_score " +
                "FROM SCORE_SUBJECT " +
                "WHERE STUDENT_ID = ? AND DELETED = 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("average_score");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }
    public List<ScoreSubject> getTopStudentsByCycle(String cycleId) {
        List<ScoreSubject> scores = new ArrayList<>();
        String sql = "SELECT s.STUDENT_ID AS student_id, u.NAME AS student_name, AVG(s.SCORE_AVERAGE) AS average_score "
                + "FROM SCORE_SUBJECT s "
                + "LEFT JOIN SUBJECT sub ON s.SUBJECT_ID = sub.ID "
                + "LEFT JOIN CYCLE c ON sub.CYCLE_ID = c.ID "
                + "LEFT JOIN STUDENT u ON s.STUDENT_ID = u.ID "
                + "WHERE s.DELETED = 0 ";
        if (!"all".equals(cycleId)) {
            sql += "AND c.ID = ? ";
        }
        sql += "GROUP BY s.STUDENT_ID, u.NAME "
                + "ORDER BY average_score DESC "
                + "FETCH FIRST 5 ROWS ONLY";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (!"all".equals(cycleId)) {
                ps.setString(1, cycleId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String studentName = rs.getString("student_name");
                double averageScore = rs.getDouble("average_score");
                Student user = new Student();
                user.setId(studentId);
                user.setName(studentName);
                ScoreSubject scoreSubject = new ScoreSubject();
                scoreSubject.setStudent(user);
                scoreSubject.setScore_average(averageScore);
                scores.add(scoreSubject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

}

