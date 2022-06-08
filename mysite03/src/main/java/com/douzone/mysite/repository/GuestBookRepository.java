package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestBookVo;


@Repository
public class GuestBookRepository {
	
	@Autowired
	private  DataSource dataSource;
	
	public List<GuestBookVo> findAll() {
		List<GuestBookVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql =
				" SELECT  "
				+ "  a.name, a.password, a.message, date_format(a.regdate, '%Y-%m-%d') as regdate, a.no "
				+ "FROM guestbook a "
				+ " order by no desc";
			pstmt = connection.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {				
				GuestBookVo vo = new GuestBookVo();
				vo.setName(rs.getString(1));
				vo.setPassword(rs.getString(2));
				vo.setMessage(rs.getString(3));
				vo.setRegdate(rs.getString(4));
				vo.setNo(rs.getLong(5));
				
				
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
	
	public static boolean insert(GuestBookVo vo){
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = dataSource.getConnection();
				
			String sql = " INSERT INTO guestbook "
					+ " VALUES "
					+ " (null, ? , ?, ?, DATE_FORMAT(CURDATE(), '%Y-%m-%d')) ";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setString(1,vo.getName());
			pstmt.setString(2,vo.getPassword());
			pstmt.setString(3,vo.getMessage());
			
					
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			//System.out.println("드라이버 로딩 실패:" + e);
			throw new GuestbookRepositoryException(e.toString());
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
	
	public boolean delete(long value, String password) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		boolean result = false;	
		
		try {
			connection = dataSource.getConnection();
				
			String sql = " delete from guestbook where no = ? and password = ?";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setLong(1, value);
			pstmt.setString(2, password);			
					
			int count = pstmt.executeUpdate();
			result = count == 1 ? true : false;
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


}