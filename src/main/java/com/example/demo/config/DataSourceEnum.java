package com.example.demo.config;

public enum DataSourceEnum {
	MASTER("master"),
	STUDENT("student");
	private String value;
	
	DataSourceEnum(String value) {
		this.setValue(value);
	}

	/**
	 * 根据注解获取数据源
	 *
	 * @paramdataSource
	 * @return
	 */
	public static DataSourceEnum getDataSourceKey(DataSource dataSource) {
		if (dataSource == null) {
			return MASTER;
		}

		return dataSource.value();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
