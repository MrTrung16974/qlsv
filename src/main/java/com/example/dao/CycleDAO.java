package com.example.dao;

import com.example.model.Cycle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CycleDAO extends DBConnect{
    public List<Cycle> getAll() {
        List<Cycle> cycleList = new ArrayList<>();
        String sql = "SELECT * FROM CYCLE WHERE DELETED = 0";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cycle cycle = new Cycle();
                cycle.setId(rs.getString("ID"));
                cycle.setName(rs.getString("NAME"));
                cycle.setDescription(rs.getString("DESCRIPTION"));
                cycle.setStartDate(rs.getDate("START_DATE"));
                cycle.setEndDate(rs.getDate("END_DATE"));
                cycle.setCreateAt(rs.getDate("CREATED_AT"));
                cycle.setLastmodified(rs.getDate("LASTMODIFIED"));
                cycle.setDeleted(rs.getBoolean("DELETED"));
                cycle.setStatus(rs.getBoolean("STATUS"));
                cycleList.add(cycle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cycleList;
    }
    public List<Cycle> getCyclesByStudentId(String studentId) {
        List<Cycle> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.ID, c.NAME " +
                "FROM CYCLE c " +
                "JOIN SUBJECT s ON c.ID = s.CYCLE_ID " +
                "JOIN SCORE_SUBJECT ss ON ss.SUBJECT_ID = s.ID " +
                "WHERE ss.STUDENT_ID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cycle cycle = new Cycle();
                cycle.setId(rs.getString("ID"));
                cycle.setName(rs.getString("NAME"));
                list.add(cycle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Cycle findById(String id) {
        String sql = "SELECT * FROM CYCLE WHERE ID = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Cycle cycle = new Cycle();
                cycle.setId(rs.getString("ID"));
                cycle.setName(rs.getString("NAME"));
                cycle.setDescription(rs.getString("DESCRIPTION"));
                cycle.setStartDate(rs.getDate("START_DATE"));
                cycle.setEndDate(rs.getDate("END_DATE"));
                cycle.setCreateAt(rs.getDate("CREATED_AT"));
                cycle.setLastmodified(rs.getDate("LASTMODIFIED"));
                cycle.setDeleted(rs.getBoolean("DELETED"));
                cycle.setStatus(rs.getBoolean("STATUS"));
                return cycle;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void add(Cycle cycle) {
        String sql = "INSERT INTO cycle (id, name, description, start_date, end_date, created_at, lastmodified, deleted, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cycle.getId());
            ps.setString(2, cycle.getName());
            ps.setString(3, cycle.getDescription());
            ps.setDate(4, new java.sql.Date(cycle.getStartDate().getTime()));
            ps.setDate(5, new java.sql.Date(cycle.getEndDate().getTime()));
            ps.setDate(6, new java.sql.Date(cycle.getCreateAt().getTime()));
            ps.setDate(7, new java.sql.Date(cycle.getLastmodified().getTime()));
            ps.setBoolean(8, cycle.isDeleted());
            ps.setBoolean(9, cycle.isStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cycle cycle) {
        String sql = "UPDATE cycle SET name = ?, description = ?, start_date = ?, end_date = ?, "
                + "lastmodified = ?, deleted = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cycle.getName());
            ps.setString(2, cycle.getDescription());
            ps.setDate(3, new java.sql.Date(cycle.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(cycle.getEndDate().getTime()));
            ps.setDate(5, new java.sql.Date(cycle.getLastmodified().getTime()));
            ps.setBoolean(6, cycle.isDeleted());
            ps.setBoolean(7, cycle.isStatus());
            ps.setString(8, cycle.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "UPDATE cycle SET deleted = 1 WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
