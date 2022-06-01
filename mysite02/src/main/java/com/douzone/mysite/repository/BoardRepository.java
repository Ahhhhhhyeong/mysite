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

	/* ------------------------------------
	 * 게시판 초기화면
	 * 페이징, 게시글 리스트(테이블) 구해옴
	 -------------------------------------- */
	public List<BoardVo> findAll(int pages, String kwd) {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();		
			if(kwd != null) {
				String sql =
						" SELECT a.no, a.title, a.contents, a.hit, "
						+ "a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no "
						+ " FROM board a , user b "
						+ " WHERE a.user_no = b.no"
						+ " AND a.title like ? "
						+ " ORDER BY g_no desc, o_no asc, depth asc  "
						+ " limit 5";			
					pstmt = connection.prepareStatement(sql);
					pstmt.setString(1, "%" + kwd + "%");
			}else {		
				String sql =
					" SELECT a.no, a.title, a.contents, a.hit, "
					+ "a.reg_date, a.g_no, a.o_no, a.depth, b.name, a.user_no "
					+ " FROM board a , user b "
					+ " WHERE a.user_no = b.no "
					+ " ORDER BY g_no desc, o_no asc, depth asc  "
					+ " limit ?, 5";			
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, (pages - 1) * 5);	
			}
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

	@SuppressWarnings("resource")
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
				+ " AND a.no = ? ";
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, no);
						
			rs = pstmt.executeQuery();
			
			long hit = 0;
			while(rs.next()) {				
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				hit = rs.getLong(4);
				vo.setHit(hit);
				vo.setReg_date(rs.getString(5));
				vo.setG_no(rs.getLong(6));
				vo.setO_no(rs.getLong(7));
				vo.setDepth(rs.getLong(8));
				vo.setUser_name(rs.getString(9));
				vo.setUser_no(rs.getLong(10));
								
				result.add(vo);
			}
			
			String updateHit = "UPDATE board SET hit = ? "
					+ " WHERE no = ? ";
			pstmt = connection.prepareStatement(updateHit);
			pstmt.setLong(1, hit+1);
			pstmt.setLong(2, no);
			
			pstmt.executeUpdate();
			
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
	
	public int countBoard() {
		int result = 0;
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
			
			String sql =
				" SELECT count(*) FROM board";
			pstmt = connection.prepareStatement(sql);
						
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result = rs.getInt(1);
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
			String title = vo.getTitle();
			String contents = vo.getContents();		
			long userNo = vo.getUser_no();
			
			connection = getConnection();
			
			String sql = "INSERT INTO  "
					+ " board (title, contents, hit, reg_date, g_no, o_no, depth, user_no) "
					+ " select ?, ?, 0, now(), MAX(g_no) + 1 , 1, 0, ? "
					+ " from board";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, userNo);
								
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
	
	public void insertComent(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			String title = vo.getTitle();
			String contents = vo.getContents();
			long g_no = vo.getG_no();
			long o_no = vo.getO_no();
			long depth = vo.getDepth();
			long userNo = vo.getUser_no();
			long no = vo.getNo();
			
			if(o_no == 1) {
				o_no += 1;
			}
			
			connection = getConnection();
			// 여기 수정 해야됨
			String sql = "INSERT INTO  "
					+ " board (title, contents, hit, reg_date, g_no, o_no, depth, user_no) "
					+ " select  ?, ?, 0, now(), ? , ?, ?, ? "
					+ " from board where no = ?";
			pstmt = connection.prepareStatement(sql);			
			
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, g_no);
			pstmt.setLong(4, o_no);
			pstmt.setLong(5, depth + 1);
			pstmt.setLong(6, userNo);
			pstmt.setLong(7, no);
								
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


	public void delete(long no, long userNo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = getConnection();
				
			String sql = " DELETE FROM board "
					+ " WHERE no = ? "
					+ " AND user_no = ?";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setLong(1, no);
			pstmt.setLong(2, userNo);
								
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
	
	public void update(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
				
		try {
			connection = getConnection();
			
			String sql = " UPDATE board SET "
					+ " title = ?  "
					+ " , contents = ? "
					+ " WHERE no = ?";
			pstmt = connection.prepareStatement(sql);			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());			
			
								
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
