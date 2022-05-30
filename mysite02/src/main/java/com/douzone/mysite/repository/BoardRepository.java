package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardRepository extends BoardVo {

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql =
				" SELECT a.no, a.title, a.contents, a.hit, "
				+ "a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no "
				+ " FROM board a , user b "
				+ " WHERE a.user_no = b.no "
				+ " ORDER BY g_no desc, o_no";
			pstmt = connection.prepareStatement(sql);
						
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {				
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getLong(4));
				vo.setReg_date(rs.getString(5));
				vo.setG_no(rs.getLong(6));
				vo.setO_no(rs.getLong(7));
				vo.setDepth(rs.getLong(8));
				vo.setUser_name(rs.getString(9));
				vo.setUser_no(rs.getLong(10));
								
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;	
	}

	public List<BoardVo> findNo(int no) {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql =
				" SELECT a.no, a.title, a.contents, a.hit, "
				+ "a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no "
				+ " FROM board a , user b "
				+ " WHERE a.user_no = b.no "
				+ " AND a.no = ? "
				+ " ORDER BY g_no desc, o_no";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
						
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {				
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getLong(4));
				vo.setReg_date(rs.getString(5));
				vo.setG_no(rs.getLong(6));
				vo.setO_no(rs.getLong(7));
				vo.setDepth(rs.getLong(8));
				vo.setUser_name(rs.getString(9));
				vo.setUser_no(rs.getLong(10));
								
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;	
	}

	public void insert(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = getConnection();
				
			String sql = "INSERT INTO "
					+ " board VALUES "
					+ "(null, ?, ?, 0, now(), ?, ?, ?, ?);";
			pstmt = connection.prepareStatement(sql);			
			
								
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	private Connection getConnection() throws SQLException{
		Connection connection = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mysql://192.168.10.41:3307/webdb?charset=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");
			
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e);
		}
		return connection;		
	}



	
}
