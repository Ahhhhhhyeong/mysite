package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {
	@Autowired
	private FileUploadService fileUploadService;	
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GalleryVo> list = galleryService.getImages();
		model.addAttribute("list", list);
		return "gallery/index";
	}	
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			@RequestParam(value="comments", required=true, defaultValue="") String comment,
			@RequestParam("file") MultipartFile multipartFile) {
			
		String url = fileUploadService.restore(multipartFile);			
		GalleryVo vo = new GalleryVo();
		vo.setUrl(url);
		vo.setComment(comment);
	
		galleryService.saveImage(vo);
		
		return "redirect:/gallery";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no) {
		galleryService.removeImages(no);
		return "redirect:/gallery";
	}
	
	
}
