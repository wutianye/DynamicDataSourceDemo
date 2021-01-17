package com.example.demo.service;

import javax.transaction.Transactional;
import javax.validation.spi.ValidationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserDS.UserMapper;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	StudentService studentService;
	
	public User getUserById(String id) {
		return userMapper.selectById(id);
	}
	
	@Transactional
	public void updateUserStudentInfo(String name, String sex, String classString) {
		updateUser(name, sex);
		studentService.updateStudent(name, classString);
	
	}
	
	private void updateUser(String name, String newSex) {
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("name",name));
		user.setSex(newSex);
		userMapper.updateById(user);
	} 

}
