package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardVo> getPageList(int page, String kwd) {
		return boardRepository.findAll(page, kwd);
	}
	
	public int totalCount(String kwd) {
		return boardRepository.totalCount(kwd);
	}

	public Map<String, Integer> getPage(int page, String kwd) {
		Map<String, Integer> map = new HashMap<>();

		int count = totalCount(kwd) - (5 * (page - 1));
		int lastPage = (totalCount(kwd) - 1) / 5 + 1;
		int startPage = 0, endPage = 0;
		if (page < 4 || lastPage <= 5) {
			startPage = 1;
			endPage = 5;
		} else if ((lastPage - page) > 1) {
			startPage = page - 2;
			endPage = page + 2;
		} else {
			endPage = lastPage;
			startPage = endPage - 4;
		}

		map.put("count", count);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("lastPage", lastPage);
		return map;
	}

	public BoardVo getView(long no) {
		return boardRepository.findByNo(no);	
	}

	public void updateHit(Long no) {
		boardRepository.updateHit(no);
	}

	public void write(BoardVo vo) {
		boardRepository.write(vo);
	}
	public void insert(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
    public void delete(Long no) {
		boardRepository.delete(no);
    }

	public void update(BoardVo vo) {
		boardRepository.update(vo);
	}
	
}
