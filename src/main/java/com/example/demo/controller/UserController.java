package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Result;
import com.example.demo.entity.Career;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController()
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/single")
	public Result<User> getUserData(@RequestParam("userId")String id) {
		return Result.success(userService.getUserById(id));
	}
	
	/**
	 * 此功能演示如何动态添加数据源
	 *在数据源未知的情况下，无法进行初始化配置，而在java代码中临时添加并使用的数据源，我们称之为动态数据源
	 *这种情况常见于各种分库操作中。
	 * @return
	 */
	@GetMapping(value = "/lover")
	public Result<List<Career>> getLoverData() {
		return Result.success(userService.getLoverData());
	}

}
