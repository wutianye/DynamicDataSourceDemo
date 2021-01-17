package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.Result;
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

}
