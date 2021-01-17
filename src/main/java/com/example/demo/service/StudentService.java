package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentDS.StudentMapper;

@Service
@DS("student")
public class StudentService {
	@Autowired
	StudentMapper studentMapper;
	
	public Student getStudentInfo(String id) {
		return studentMapper.selectById(id);
	}
	
	public void updateStudent(String name, String newClass) {
		Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("name",name));
		student.setClassName(newClass);
		//造个异常，来看看会不会回滚
		//int a = 1/0;    
		studentMapper.updateById(student);
	} 

}
