package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;


@Repository
public class GuestBookRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestBookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public boolean insert(GuestBookVo vo){		
		return sqlSession.insert("guestbook.insert", vo) == 1;
	}
	
	public boolean delete(long value, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", value);
		map.put("password", password);
		return sqlSession.delete("guestbook.delete", map) == 1;
	}

	public List<GuestBookVo> getList(Long sno) {
		return sqlSession.selectList("guestbook.getList", sno);
	}

	public int totalCount() {
		return sqlSession.selectOne("guestbook.totalCount");
	}

	public String insertContent(GuestBookVo vo) {
		int no = sqlSession.insert("guestbook.insert", vo);		
		return Integer.toString(no);
	}	


}
