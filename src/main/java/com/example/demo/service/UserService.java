package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.DataSourceContextHolder;
import com.example.demo.config.DynamicDataSource;
import com.example.demo.entity.Career;
import com.example.demo.entity.Love;
import com.example.demo.entity.User;
import com.example.demo.mapper.CareerMapper;
import com.example.demo.mapper.LoveMapper;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService {
	@Autowired

	private Environment env;

	@Autowired
	UserMapper userMapper;

	@Autowired
	LoveMapper loveMapper;

	@Autowired
	CareerMapper careerMapper;

	public User getUserById(String id) {
		return userMapper.selectById(id);
	}

	public List<Career> getLoverData() {
		Love lover = loveMapper.selectById(1);
		String name = lover.getName();

		DruidDataSource dynamicDataSource = new DruidDataSource();
		dynamicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dynamicDataSource.setUrl("jdbc:mysql://localhost:3306/" + name
				+ "?characterEncoding=UTF-8&useUnicode=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
		dynamicDataSource.setUsername("root");
		dynamicDataSource.setPassword(env.getProperty("db_password"));

		/**
		 * 创建动态数据源
		 */
		Map<Object, Object> dataSourceMap = DynamicDataSource.getInstance().getDataSourceMap();
		dataSourceMap.put(name, dynamicDataSource);
		DynamicDataSource.getInstance().setTargetDataSources(dataSourceMap);
		/**
		 * 切换为动态数据源实例
		 */
		DataSourceContextHolder.setDataSource(name);

		return careerMapper.selectList(new QueryWrapper<Career>());

	}

}
