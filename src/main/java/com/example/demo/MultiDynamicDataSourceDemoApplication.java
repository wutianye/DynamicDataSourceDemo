package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy = true)
public class MultiDynamicDataSourceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiDynamicDataSourceDemoApplication.class, args);
	}

}
