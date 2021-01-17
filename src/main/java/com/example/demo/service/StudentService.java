package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;

@Service
@DS("student")
public class StudentService {
	@Autowired
	StudentMapper studentMapper;
	
	public Student getStudentInfo(String id) {
		return studentMapper.selectById(id);
	}

}
