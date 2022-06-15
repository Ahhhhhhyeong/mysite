package com.douzone.mysite.controller;

import java.util.Map;

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
import com.douzone.mysite.web.WebUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String index(@RequestParam(value="p", required=true, defaultValue="1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword, Model model) {
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAttribute("map", map);
		
		return "board/index";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getView(no);
		boardService.updateHit(no);
		model.addAttribute("vo", boardVo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, BoardVo boardVo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {
		boardVo.setUser_no(authUser.getNo());
		boardService.insert(boardVo);
		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	@Auth
	@RequestMapping(value = "/write/{no}")
	public String reply(@PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getView(no);
		boardVo.setO_no(boardVo.getO_no() + 1);
		boardVo.setDepth(boardVo.getDepth() + 1);

		model.addAttribute("vo", boardVo);

		return "board/write";
	}
	@Auth
	@RequestMapping("/modify/{no}") 
	public String modify(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		BoardVo boardVo = boardService.getView(no);
		model.addAttribute("vo", boardVo);
		return "board/modify";
	}

	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser,BoardVo vo){
		boardService.update(vo);
		return "redirect:/board";
	}

	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@AuthUser UserVo authUser, @PathVariable("no") Long no){
		boardService.delete(no, authUser.getNo());
		return "redirect:/board";
	}

	
}
