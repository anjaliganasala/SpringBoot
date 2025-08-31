package com.flm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flm.dao.UserRepository;
import com.flm.model.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public String save() {
		
		User user = new User("Anjali",10);
		User savedUser = userRepository.save(user);
		return savedUser.getName();
		
	}
	
	public void getUser() {
		
		Optional<User> userInfo = userRepository.findById(1);
		User user = userInfo.get();
		System.out.println(user);
	}
	
	public void getAllUsers() {
		List<User> users = userRepository.findAll();
		System.out.println(users);
	}
    public void getUserByName() {
    	User user2 = userRepository.findByUserName("Anjali");
    	System.out.println(user2);
    }
    public void getUserByAge() {
    	User user3 = userRepository.findByUserAge(10);
    	System.out.println(user3);
    }
    public void findByName() {
    	User user4 = userRepository.findByName("Anjali");
    	System.out.println(user4);
    }
    public User findByNameAndAge(String name, int age) {
       User user  = userRepository.findByNameAndAge(name,age);
       return user;
    }
    public User findByNameOrAge(String name , int age) {
    	User user = userRepository.findByNameOrAge(name, age);
    	return user;
    }
    public List<User> findByNameStartingWith(String name) {
    	List<User> user = userRepository.findByNameStartingWith(name);
    	return user;
    }

 
}
