package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.DataSource;
import com.example.demo.config.DataSourceKeyEnum;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;

@Service

public class StudentService {
	@Autowired
	StudentMapper studentMapper;
	
	@DataSource(DataSourceKeyEnum.STUDENT)
	public Student getStudentInfo(int id) {
		return studentMapper.selectById(id);
	}
	
	@DataSource(DataSourceKeyEnum.STUDENT)
	public void updateStudent(String name, String newClass) {
		Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("name",name));
		student.setClassName(newClass);
		//造个异常，来看看会不会回滚
		//int a = 1/0;    
		studentMapper.updateById(student);
	} 

}
