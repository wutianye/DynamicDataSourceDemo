package com.example.demo.config;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(-1)
public class DataSourceAspect {

	@Pointcut("@annotation(com.example.demo.config.DataSource)")
	public void pointCut() {
	}

	@Around("pointCut()")
	public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DataSource dataSource = method.getAnnotation(DataSource.class);
		DataSourceEnum keyEnum = DataSourceEnum.getDataSourceKey(dataSource);
		System.out.println("选择的数据源：" + keyEnum.getValue());
		DataSourceContextHolder.setDataSource(keyEnum.getValue());
		Object o = joinPoint.proceed();
		DataSourceContextHolder.clear();
		return o;
	}
}
