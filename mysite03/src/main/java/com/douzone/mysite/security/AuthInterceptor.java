package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Handler 종류 확인(캐스팅 오류 대비)
		if(!(handler instanceof HandlerMethod)) {
			// Default servlet handler가 처리하는 정적 자원
			return true;			
		}
		
		// @Auth 가 있는지 없는지 체크(Annotated?)
		// casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// Handler Method의 @Auth 받아오는 작업
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// Handler Method에 @Auth가 안붙어 있는 경우
		if(auth == null) {
			return true;
		}
		
		// Handler Method에 @Auth가 붙어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// @Auth가 적용되어 있지만 인증이 되어 있지 않음
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// @Auth가 적용되어 있고 인증도 되어 있는 상태
		return true;
	}
}
