package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({DBConfig.class, MyBatisConfig.class})
public class MyBatisConfig {
	
	@Bean
	public SqlSessionFactory sqlSession(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:com/douzone/mysite/config/app/mybatis/configuration.xml"));

		return sqlSessionFactory.getObject();
	}
}
