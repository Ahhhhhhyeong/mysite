package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
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
	public String view(@AuthUser UserVo authUser, @RequestParam(value="no", required = true, defaultValue = "") long no,
			Model model) {
		BoardVo vo = boardService.getView(no);
		boardService.updateHit(no);
		
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = {"/write", "/write/{no}"})
	public String write(@AuthUser UserVo authUser, 
		@PathVariable(value = "no", required = false) Long no,
		Model model){		
		if(no != null) {
			BoardVo vo = boardService.getView(no);
			model.addAttribute("vo", vo);			
		}
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,BoardVo vo){
		vo.setUser_no(authUser.getNo());
		boardService.write(vo);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, @PathVariable(value="no", required = false) Long no,
					Model model){
		BoardVo vo = boardService.getView(no);
		model.addAttribute("vo", vo);
		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser,BoardVo vo){
		boardService.update(vo);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@AuthUser UserVo authUser, @PathVariable(value = "no", required = false) Long no){
		boardService.delete(no);	
		return "redirect:/board";
	}

	
}
