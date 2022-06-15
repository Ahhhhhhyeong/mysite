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
	
	private static final int LIST_SIZE = 5; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 페이지 리스트의 페이지 수
	
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
	
    public void delete(Long no, Long userNo) {
		boardRepository.delete(no, userNo);
    }

	public void update(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	
	public Map<String, Object> getContentsList(int currentPage, String keyword) {

		// 1. 페이징을 위한 기본 데이터 계산
		int totalCount = boardRepository.totalCount(keyword);
		int pageCount = (int) Math.ceil((double) totalCount / LIST_SIZE);
		int blockCount = (int) Math.ceil((double) pageCount / PAGE_SIZE);
		int currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);

		// 2. 파라미터 page 값 검증
		if (currentPage > pageCount) {
			currentPage = pageCount;
			currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);
		}

		if (currentPage < 1) {
			currentPage = 1;
			currentBlock = 1;
		}

		// 3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		int prevPage = (currentBlock > 1) ? (currentBlock - 1) * PAGE_SIZE : 0;
		int nextPage = (currentBlock < blockCount) ? currentBlock * PAGE_SIZE + 1 : 0;
		int endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;

		// 4. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeword(keyword, currentPage, LIST_SIZE);

		// 5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("listSize", LIST_SIZE);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);

		return map;
	}
}
