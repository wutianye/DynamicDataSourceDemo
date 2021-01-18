package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Result;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;

@RestController()
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping(value = "/single")
	public Result<Student> getStudentInfo(@RequestParam("studentId") int id){
		return Result.success(studentService.getStudentInfo(id));
	}

}
