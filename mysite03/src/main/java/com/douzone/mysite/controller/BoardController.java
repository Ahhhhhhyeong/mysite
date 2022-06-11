package com.douzone.mysite.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping({"", "/{p}/{kwd}", "/{p}", "/{kwd}"})
	public String index(@RequestParam(value = "p", required = true, defaultValue = "1") int page,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd,
			Model model) {
		
		model.addAttribute("currentPage", page);
		model.addAttribute("kwd", kwd);
		model.addAttribute("list", boardService.getPageList(page, kwd));
		model.addAttribute("count", boardService.getPage(page, kwd).get("count"));
		model.addAttribute("startPage", boardService.getPage(page, kwd).get("startPage"));
		model.addAttribute("endPage", boardService.getPage(page, kwd).get("endPage"));
		model.addAttribute("lastPage", boardService.getPage(page, kwd).get("lastPage"));
		
		return "board/index";
	}
	
	@RequestMapping("/view")
	public String view(@RequestParam(value="no", required = true, defaultValue = "") long no,
			Model model) {
		BoardVo vo = boardService.getView(no);
		boardService.updateHit(no);
		
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping(value = {"/write", "/write/{no}"})
	public String write(HttpSession session,
				@PathVariable(value = "no", required = false) Long no,
				Model model){
		/*******************접근제어**********************/
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/board";
		}
		//////////////////////////////////////////////////
		BoardVo vo = boardService.getView(no);
		model.addAttribute("vo", vo);
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(HttpSession session, BoardVo vo){
		/*******************접근제어**********************/
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if(authUser == null){
			return "redirect:/board";
		}
		//////////////////////////////////////////////////
		vo.setUser_no(authUser.getNo());
		boardService.write(vo);
		return "board/write";
	}

	



	
	
}
