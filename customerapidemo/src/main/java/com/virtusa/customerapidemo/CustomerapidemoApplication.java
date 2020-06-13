package com.virtusa.customerapidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)  //exclude the db from auto configuration
public class CustomerapidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerapidemoApplication.class, args);
	}

}
