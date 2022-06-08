package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;


@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql =
				" SELECT no, name "
				+ "FROM user "
				+ "WHERE email = ? "
				+ "  AND password = ?";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();
						
			if(rs.next()) {				
				UserVo userVo = new UserVo();
				userVo.setNo(rs.getLong(1));
				userVo.setName(rs.getString(2));
		
				result = new UserVo();
				result = userVo;
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
	
	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql =
				" SELECT "
				+ "	no, name, email, gender "
				+ "FROM user "
				+ "WHERE "
				+ " no = ? ";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
						
			if(rs.next()) {				
				UserVo userVo = new UserVo();
				userVo.setNo(rs.getLong(1));
				userVo.setName(rs.getString(2));
				userVo.setEmail(rs.getString(3));
				userVo.setGender(rs.getString(4));
		
				result = new UserVo();
				result = userVo;
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
	
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = dataSource.getConnection();
				
			String sql = " insert  "
					+ " into user "
					+ " values (null, ?, ?, ?, ?,now()); ";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getEmail());
			pstmt.setString(3,vo.getPassword());
			pstmt.setString(4,vo.getGender());
			
					
			int count = pstmt.executeUpdate();
			result = count == 1;
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
		return result;
	}
	

	public boolean update(UserVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			if("".equals(vo.getPassword())) {
				String sql =
						" update user " + 
						"    set name=?, gender=?" + 
						"  where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());
			} else {
				String sql =
						" update user " + 
						"    set name=?, gender=?, password=?" + 
						"  where no=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setString(3, vo.getPassword());
				pstmt.setLong(4, vo.getNo());
			}
			
			int count = pstmt.executeUpdate();
			result = count == 1;			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}

	public void updateNotInPassword(UserVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = dataSource.getConnection();
				
			String sql = " UPDATE user "
					+ "SET  "
					+ "    name = ?, "
					+ "    gender = ? "
					+ "WHERE\r\n"
					+ "    email = ? ";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getGender());
			pstmt.setString(3,vo.getEmail());
								
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

}
