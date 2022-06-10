package com.douzone.mysite.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;

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
	
	
}
