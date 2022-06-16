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

    public int delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);

		return sqlSession.delete("board.delete", map);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}
	
	public boolean insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo) == 1; 
	}

	public List<BoardVo> findAllByPageAndKeword(String keyword, int page, int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", '%' + keyword + '%');
		map.put("startIndex", (page - 1) * size);
		map.put("size", size);

		return sqlSession.selectList("board.findAllByPageAndKeword", map);
	}
	
	

}
