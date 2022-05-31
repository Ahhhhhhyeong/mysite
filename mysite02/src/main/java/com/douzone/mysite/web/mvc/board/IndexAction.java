package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNum = request.getParameter("p");
		if(pageNum == null) {
			pageNum = "1";
		}
		int limit = 5;
		
		HashMap<String, Integer> pages = new HashMap<String, Integer>();
		int currentPage = Integer.parseInt(pageNum);
		
		int startPage = limit * ((int)Math.ceil((double)currentPage/5)-1)+1;
		if(startPage < 1) {
			startPage = 1;
		}
		/* 전체페이지, 마지막페이지, 이전페이지, 다음페이지 */
		int totalPage = (new BoardRepository().countBoard() % limit) == 0 ? new BoardRepository().countBoard()/limit : new BoardRepository().countBoard()/limit+1;
		int lastPage = startPage + (limit - 1) > totalPage ? totalPage : startPage + (limit-1);
		int prevPage = currentPage - 1 < 0 ? 1 : currentPage - 1;
		int nextPage = currentPage + 1 > totalPage ? totalPage : currentPage + 1;
		
		pages.put("currentPage", currentPage);
		pages.put("startPage", startPage);
		pages.put("totalPage", totalPage);
		pages.put("lastPage", lastPage);
		pages.put("prevPage", prevPage);
		pages.put("nextPage", nextPage);
		
		int totalBoard = new BoardRepository().countBoard();
		request.setAttribute("totalBoard", totalBoard);
				
		List<BoardVo> list = new BoardRepository().findAll(currentPage);
		
		request.setAttribute("list", list);
		request.setAttribute("pages", pages);
		
		
		
		WebUtil.forward(request, response, "board/index");
	}

}
