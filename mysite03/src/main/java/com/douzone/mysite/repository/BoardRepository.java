package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository extends BoardVo {
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAll(int page, String kwd) {
		page = (page - 1) * 5;

		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("kwd", "%" + kwd + "%");

		return sqlSession.selectList("board.findAll", map);
	}

	public int totalCount(String kwd) {
		return sqlSession.selectOne("board.totalCount", "%" + kwd + "%");
	}

	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);
	}

	public boolean updateHit(Long no) {
		return sqlSession.update("board.updateHit", no) == 1;
	}

    public void write(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
    }

    public void delete(Long no) {
		sqlSession.delete("board.delete", no);
    }

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	

}
