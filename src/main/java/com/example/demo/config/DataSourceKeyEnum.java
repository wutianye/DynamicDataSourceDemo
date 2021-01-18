package com.example.demo.config;

public enum DataSourceKeyEnum {
 MASTER("master"),

 STUDENT("student");


 private String value;

 DataSourceKeyEnum(String value) {
   this.setValue(value);
 }

 /**
  * 根据注解获取数据源
  *
  * @param dataSource
  * @return
  */
 public static DataSourceKeyEnum getDataSourceKey(DataSource dataSource) {
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
