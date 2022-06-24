package com.douzone.mysite.initializer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.douzone.mysite.MySiteApplication;

public class SpringBootInitializer extends SpringBootServletInitializer {
	@Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(MySiteApplication.class);
	   }
}
