package com.example.dao;

import com.example.model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreSubjectDAO extends DBConnect {
    public List<ScoreSubject> getScoreSubjectByTeacherAndStudent(String teacherId, String studentId) {
        List<ScoreSubject> scoreSubjects = new ArrayList<>();
        String sql = "SELECT DISTINCT " +
                "    st.ID AS student_id, " +
                "    st.NAME AS student_name, " +
                "    sb.ID AS subject_id, " +
                "    sb.NAME AS subject_name, " +
                "    sb.START_DATE AS subject_start_date, " +
                "    sb.END_DATE AS subject_end_date, " +
                "    COALESCE(ss.SCORE_PROCESS, 0.0) AS score_process, " +
                "    COALESCE(ss.SCORE_FINAL, 0.0) AS score_final, " +
                "    COALESCE(ss.SCORE_AVERAGE, 0.0) AS score_average, " +
                "    ss.ID AS score_subject_id " +
                "FROM QLSV.CLASS_STUDENT cs " +
                "JOIN QLSV.STUDENT st ON st.ID = cs.STUDENT_ID " +
                "JOIN QLSV.CLASS c ON c.ID = cs.CLASS_ID " +
                "JOIN QLSV.SUBJECT sb ON sb.TEACHER_ID = c.TEACHER_ID " +
                "LEFT JOIN QLSV.SCORE_SUBJECT ss ON ss.STUDENT_ID = st.ID AND ss.SUBJECT_ID = sb.ID " +
                "WHERE st.ID = ? " +
                "  AND c.TEACHER_ID = ? " +
                "  AND st.DELETED = 0 " +
                "  AND c.DELETED = 0 " +
                "  AND sb.DELETED = 0";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studentId);
            pst.setString(2, teacherId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setId(rs.getString("subject_id"));
                    subject.setName(rs.getString("subject_name"));
                    subject.setStartDate(rs.getDate("subject_start_date"));
                    subject.setEndDate(rs.getDate("subject_end_date"));

                   Student user = new Student();
                    user.setId(rs.getString("student_id"));
                    user.setName(rs.getString("student_name"));

                    ScoreSubject scoreSubject = new ScoreSubject();
                    scoreSubject.setId(rs.getString("score_subject_id"));
                    scoreSubject.setScoreProcess(rs.getDouble("score_process"));
                    scoreSubject.setScoreFinal(rs.getDouble("score_final"));
                    scoreSubject.setScore_average(rs.getDouble("score_average"));
                    scoreSubject.setStudent(user);
                    scoreSubject.setSubject(subject);
                    scoreSubjects.add(scoreSubject);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi truy vấn điểm sinh viên theo giáo viên", e);
        }

        return scoreSubjects;
    }

    public ScoreSubject findById(String id) {
        String sql = "select * from SCORE_SUBJECT where ID = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Subject subject = new Subject();
                subject.setId(rs.getString("SUBJECT_ID"));
                Student student = new UserDAO().findStudentById(rs.getString("STUDENT_ID"));
                ScoreSubject scoreSubject = new ScoreSubject(
                        rs.getString("ID"),
                        rs.getDouble("SCORE_PROCESS"),
                        rs.getDouble("SCORE_FINAL"),
                        rs.getDouble("SCORE_AVERAGE"),
                        rs.getDate("CREATED_AT"),
                        rs.getDate("LASTMODIFIED"),
                        rs.getBoolean("DELETED"),
                        subject, student);
                return scoreSubject;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addScoreSubject(ScoreSubject scoreSubject) {
        String sql = "INSERT INTO SCORE_SUBJECT (ID, SCORE_PROCESS, SCORE_FINAL,SCORE_AVERAGE,CREATED_AT ,LASTMODIFIED, DELETED, STUDENT_ID, SUBJECT_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?,?,?,?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, scoreSubject.getId());
            pst.setDouble(2, scoreSubject.getScoreProcess());
            pst.setDouble(3, scoreSubject.getScoreFinal());
            pst.setDouble(4, scoreSubject.getScore_average());
            pst.setDate(5,scoreSubject.getCreateAt());
            pst.setDate(6,scoreSubject.getLassmodified());
            pst.setBoolean(7,scoreSubject.isDeleted());
            if (scoreSubject.getStudent() != null && scoreSubject.getStudent().getId() != null) {
                pst.setString(8, scoreSubject.getStudent().getId());
            } else {
                pst.setNull(8, Types.VARCHAR);
            }
            pst.setString(9, scoreSubject.getSubject().getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateScoreSubject(ScoreSubject scoreSubject) {
        String sql = "UPDATE SCORE_SUBJECT SET " +
                "SCORE_PROCESS = ?, " +
                "SCORE_FINAL = ?, " +
                "SCORE_AVERAGE = ?, " +
                "LASTMODIFIED = ?, "+
                "SUBJECT_ID = ?, " +
                "STUDENT_ID = ? " +
                "WHERE ID = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, scoreSubject.getScoreProcess());
            pst.setDouble(2, scoreSubject.getScoreFinal());
            pst.setDouble(3, scoreSubject.getScore_average());
            pst.setDate(4, scoreSubject.getLassmodified());
            pst.setString(5, scoreSubject.getSubject().getId());
            pst.setString(6,scoreSubject.getStudent().getId());
            pst.setString(7, scoreSubject.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ScoreSubject> getScoreSubjectByStudentId(String id) {
        List<ScoreSubject> list = new ArrayList<>();
        String sql = "SELECT " +
                "subj.NAME AS subject_name, " +
                "c.NAME AS semester_name, " +
                "ss.SCORE_PROCESS, " +
                "ss.SCORE_FINAL, " +
                "ss.SCORE_AVERAGE, " +
                "subj.PROCESS_COEFFICIENT, " +
                "subj.EXAM_COEFFICIENT " +
                "FROM SCORE_SUBJECT ss " +
                "JOIN SUBJECT subj ON ss.SUBJECT_ID = subj.ID " +
                "JOIN CYCLE c ON subj.CYCLE_ID = c.ID " +
                "WHERE ss.STUDENT_ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setProcessCoefficient(rs.getDouble("PROCESS_COEFFICIENT"));
                subject.setExamCoefficient(rs.getDouble("EXAM_COEFFICIENT"));

                Cycle cycle = new Cycle();
                cycle.setName(rs.getString("semester_name"));
                subject.setCycle(cycle);

                ScoreSubject ss = new ScoreSubject();
                ss.setScoreProcess(rs.getDouble("SCORE_PROCESS"));
                ss.setScoreFinal(rs.getDouble("SCORE_FINAL"));
                ss.setScore_average(rs.getDouble("SCORE_AVERAGE"));
                ss.setSubject(subject);

                list.add(ss);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<ScoreSubject> getScoreSubjectByStudentIdAndCycleId(String id, String cycleId) {
        List<ScoreSubject> list = new ArrayList<>();
        String sql = "SELECT " +
                "subj.NAME AS subject_name, " +
                "c.NAME AS semester_name, " +
                "ss.SCORE_PROCESS, " +
                "ss.SCORE_FINAL, " +
                "ss.SCORE_AVERAGE, " +
                "subj.PROCESS_COEFFICIENT, " +
                "subj.EXAM_COEFFICIENT " +
                "FROM SCORE_SUBJECT ss " +
                "JOIN SUBJECT subj ON ss.SUBJECT_ID = subj.ID " +
                "JOIN CYCLE c ON subj.CYCLE_ID = c.ID " +
                "WHERE ss.STUDENT_ID = ? and c.ID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, cycleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject();
                subject.setName(rs.getString("subject_name"));
                subject.setProcessCoefficient(rs.getDouble("PROCESS_COEFFICIENT"));
                subject.setExamCoefficient(rs.getDouble("EXAM_COEFFICIENT"));

                Cycle cycle = new Cycle();
                cycle.setName(rs.getString("semester_name"));
                subject.setCycle(cycle);

                ScoreSubject ss = new ScoreSubject();
                ss.setScoreProcess(rs.getDouble("SCORE_PROCESS"));
                ss.setScoreFinal(rs.getDouble("SCORE_FINAL"));
                ss.setScore_average(rs.getDouble("SCORE_AVERAGE"));
                ss.setSubject(subject);

                list.add(ss);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<ScoreSubject> getScoreSubjectByClassAndStudent(String classId, String studentId) {
        List<ScoreSubject> scoreSubjects = new ArrayList<>();
        String sql = "SELECT " +
                "    u.ID AS student_id, " +
                "    u.NAME AS student_name, " +
                "    s.ID AS subject_id, " +
                "    s.NAME AS subject_name, " +
                "    COALESCE(ss.SCORE_PROCESS, 0) AS score_laborious, " +
                "    COALESCE(ss.SCORE_FINAL, 0) AS score_final, " +
                "    COALESCE(ss.SCORE_AVERAGE, 0) AS score_average, " +
                "    ss.ID AS score_subject_id " +
                "FROM CLASS_STUDENT cs " +
                "JOIN STUDENT u ON u.ID = cs.STUDENT_ID " +
                "JOIN CLASS c ON c.ID = cs.CLASS_ID " +
                "JOIN SUBJECT s ON s.TEACHER_ID = c.TEACHER_ID " +
                "LEFT JOIN SCORE_SUBJECT ss ON ss.SUBJECT_ID = s.ID AND ss.STUDENT_ID = u.ID AND ss.DELETED = 0 " +
                "WHERE u.ID = ? " +
                "  AND c.ID = ? " +
                "  AND u.DELETED = 0 " +
                "  AND c.DELETED = 0 " +
                "  AND s.DELETED = 0";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studentId);
            pst.setString(2, classId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setId(rs.getString("subject_id"));
                    subject.setName(rs.getString("subject_name"));

                    Student user = new Student();
                    user.setId(rs.getString("student_id"));
                    user.setName(rs.getString("student_name"));

                    ScoreSubject scoreSubject = new ScoreSubject();
                    scoreSubject.setId(rs.getString("score_subject_id"));
                    scoreSubject.setScoreProcess(rs.getDouble("score_laborious"));
                    scoreSubject.setScoreFinal(rs.getDouble("score_final"));
                    scoreSubject.setScore_average(rs.getDouble("score_average"));
                    scoreSubject.setStudent(user);
                    scoreSubject.setSubject(subject);
                    scoreSubjects.add(scoreSubject);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi truy vấn điểm sinh viên ", e);
        }

        return scoreSubjects;
    }

    public void deleteScoreSubjectById(String id) {
        String sql = "DELETE FROM SCORE_SUBJECT WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ScoreSubject> findScoresByStudentId(String studentId) throws SQLException {
        String sql = "SELECT * FROM SCORE_SUBJECT WHERE STUDENT_ID = ? AND DELETED = 0";

        List<ScoreSubject> scores = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ScoreSubject ss = new ScoreSubject();
                Student student = new UserDAO().findStudentById(rs.getString("STUDENT_ID"));
                Subject subject = new SubjectDAO().findById(rs.getString("SUBJECT_ID"));
                ss.setId(rs.getString("ID"));
                ss.setScoreProcess(rs.getDouble("SCORE_PROCESS"));
                ss.setScoreFinal(rs.getDouble("SCORE_FINAL"));
                ss.setScore_average(rs.getDouble("SCORE_AVERAGE"));
                ss.setSubject(subject);
                ss.setStudent(student);
                scores.add(ss);
            }
        }
        return scores;
    }
}

