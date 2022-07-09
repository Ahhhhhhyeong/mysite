package com.douzone.mysite.controller.api;

import java.lang.invoke.CallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JSONResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
		
	@GetMapping("")
	public JSONResult getList(@RequestParam Long sno) {		
		List<GuestBookVo> list = guestbookService.getList(sno);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", guestbookService.totalCount());
		return JSONResult.success(map);
	}
	
	@PostMapping("/insert")
	public JSONResult insert(@RequestBody GuestBookVo vo) {
		String no = guestbookService.addContent(vo);	
		return JSONResult.success(vo);
	}
	
	@DeleteMapping("/{no}")
	public JSONResult delete(@RequestParam String password, @PathVariable Long no) {
		boolean result = guestbookService.deleteMessage(no, password);
		return JSONResult.success(result);
	}
	
}
