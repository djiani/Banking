package com.virtusa.customerapidemo.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Data
@Slf4j
public class DBConfig {
	
	@Value("${db_url}")
	private String url;
	@Value("${db_username}")
	private String username;
	@Value("${db_password}")
	private String password;
	@Value("${db_driver}")
	private String driverClassName;
	
	
	@Profile("production")
	@Bean
	public DataSource prodDataSource() {
		
		DataSourceBuilder builder= DataSourceBuilder.create();
		builder.url(url);
		builder.username(username);
		builder.password(password);
		builder.driverClassName(driverClassName);
		
		log.info("Production ......");
		log.debug("test in debug.....");
		return builder.build();
	}
	
	
	@Profile("development")
	@Bean
	public DataSource devDataSource() {
		DataSourceBuilder builder= DataSourceBuilder.create();
		builder.url(url);
		builder.username(username);
		builder.password(password);
		builder.driverClassName(driverClassName);
		
		log.info("development ......");
		log.debug("test in deub.....");
		return builder.build();
	}
	
	
	@Profile("qa")
	@Bean
	public DataSource qaDataSource() {
		DataSourceBuilder builder= DataSourceBuilder.create();
		builder.url(url);
		builder.username(username);
		builder.password(password);
		builder.driverClassName(driverClassName);
		
		log.info("qa ......");
		log.debug("test in deub.....");
		return builder.build();
	}

}
