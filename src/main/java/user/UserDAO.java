package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	
	DataSource dataSource;
	
	public UserDAO() {
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM USER WHERE userID = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("userPassword").equals(userPassword)) {
					return 1; // success
				}
				return 2; // password error
			} else {
				return 0; // not exist
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) rs.close();
				if (null != pstmt) pstmt.close();
				if (null != conn) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //database error
	}
	
	public int registerCheck(String userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "SELECT * FROM USER WHERE userID = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			if (rs.next() || userID.equals("")) {
				return 0; // duplication
			} else {
				return 1; // possible
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) rs.close();
				if (null != pstmt) pstmt.close();
				if (null != conn) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //database error
	}
	
	public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userID);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			rs = pstmt.executeQuery();
			
			if (rs.next() || userID.equals("")) {
				return 0; // duplication
			} else {
				return 1; // possible
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != rs) rs.close();
				if (null != pstmt) pstmt.close();
				if (null != conn) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return -1; //database error
	}

}
