package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiDynamicDataSourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDynamicDataSourceDemoApplication.class, args);
	}

}
