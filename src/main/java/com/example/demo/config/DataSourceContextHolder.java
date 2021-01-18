package com.example.demo.config;

public class DataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

	public static void setDataSource(String db) {
		contextHolder.set(db);
	}

	public static String getDataSource() {
		return contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}

}