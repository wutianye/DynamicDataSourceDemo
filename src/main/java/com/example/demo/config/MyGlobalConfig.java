package com.example.demo.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyGlobalConfig extends GlobalConfig {

  @Autowired
  private CustomSqlSessionTemplate sqlSessionTemplate;

  private static CustomSqlSessionTemplate mySqlSessionTemplate;

  @Override
  public SqlSessionFactory getSqlSessionFactory() {
    return mySqlSessionTemplate.getSqlSessionFactory();
  }

  @PostConstruct
  public void init() {
    MyGlobalConfig.mySqlSessionTemplate = sqlSessionTemplate;
  }
}

