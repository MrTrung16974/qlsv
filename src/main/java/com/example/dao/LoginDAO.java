package com.example.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.model.Student;
import com.example.model.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginDAO extends DBConnect{

	public Teacher login(String username, String password) {
		String sql = "SELECT * FROM TEACHER WHERE ID = ? AND DELETED = 0";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String hashedPassword = rs.getString("PASSWORD");
				BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
				if (result.verified) {
					return new Teacher(
							rs.getString("ID"),
							rs.getString("NAME"),
							rs.getString("PHONE"),
							rs.getString("MAIL"),
							rs.getDate("DATE_OF_BIRTH"),
							rs.getInt("TYPE"),
							null,
							rs.getDate("CREATE_AT"),
							rs.getDate("LASTMODIFIED"),
							rs.getBoolean("DELETED"),
							rs.getBoolean("STATUS")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	public Student loginStudent(String username, String password) {
		String sql = "SELECT * FROM STUDENT WHERE ID = ? AND DELETED = 0";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String hashedPassword = rs.getString("PASSWORD");
				BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
				if (result.verified) {
					return new Student(
							rs.getString("ID"),
							rs.getString("NAME"),
							rs.getString("PHONE"),
							rs.getString("EMAIL"),
							rs.getDate("DATE_OF_BIRTH"),
							rs.getString("ADDRESS"),
							null,
							rs.getDate("START_YEAR"),
							rs.getDate("END_YEAR"),
							rs.getDate("CREATE_AT"),
							rs.getDate("LASTMODIFIED"),
							rs.getBoolean("DELETED"),
							rs.getBoolean("STATUS")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
//	public Teacher getLoginByUserId(String userId) {
//		String sql = "SELECT * FROM TEACHER WHERE ID = ?";
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, userId);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				Teacher users = new UserDAO().findById(rs.getString("user_id"));
//				Login login = new Login();
//				login.setId(rs.getString("id"));
//				login.setUsername(rs.getString("username"));
//				login.setPassword(rs.getString("password"));
//				login.setDeleted(rs.getBoolean("deleted"));
//				login.setUsers(users);
//				return login;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	public void updatePassByUserId(String userId, String password) {
		String sql = "UPDATE TEACHER SET PASSWORD = ? WHERE ID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updatePassByStudentId(String userId, String password) {
		String sql = "UPDATE STUDENT SET PASSWORD = ? WHERE ID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
