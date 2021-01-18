package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.config.DataSource;
import com.example.demo.config.DataSourceKeyEnum;
import com.example.demo.entity.Student;

public interface StudentMapper extends BaseMapper<Student>{
	
	//Student selectById(@Param("id")int id);

}
