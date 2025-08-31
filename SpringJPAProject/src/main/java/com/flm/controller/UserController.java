package com.flm.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.model.User;
import com.flm.service.UserPageService;
import com.flm.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	UserPageService userPageService;
	
	@GetMapping("/save")
	public String saveUser(){
		String name = userService.save();
		return name;
		
	}
	
	@GetMapping("/user")
	public void findUser() {
		userService.getUser();
	}
	
	@GetMapping("/allusers")
	public void findAllUsers() {
		userService.getAllUsers();
	}
    @GetMapping("/userbyname")
	public void findUserByName() {
		userService.getUserByName();
	}
    
    @GetMapping("/userbyage")
    public void findUserByAge() {
    	userService.getUserByAge();
    }
    @GetMapping("/findbyname")
    public void findByName(){
    	userService.findByName();
    }
    @GetMapping("/bynameandage")
    public User findByNameAndAge(@RequestParam(name= "name") String name , @RequestParam(name ="age") int  age) {
    	User user = userService.findByNameAndAge(name, age);
    	return user;
    	
    }
    @GetMapping("/bynameorage")
    public User findByNameOrAge(String name, int age) {
    	User user = userService.findByNameOrAge(name, age);
    	return user;
    }
    @GetMapping("/bynamestartingwith")
    public List<User> findByNameStartingWith(String name) {
    	List<User> user = userService.findByNameStartingWith(name);
    	return user;
    }
    
    @GetMapping("/bypage")
    public Page<User> getUsersByPage(@RequestParam (name = "page") int page, @RequestParam(name = "size") int size){
    	return userPageService.getUserByPage(page,size);
    }
    }

