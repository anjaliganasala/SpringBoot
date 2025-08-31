package com.flm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flm.dao.UserRepository;
import com.flm.model.User;

@Service
public class UserPageService {

	@Autowired
	UserRepository repository;
		
	public Page<User> getUserByPage(int pageNumber , int size) {
		PageRequest request = PageRequest.of(pageNumber,size);
		Page<User> page = repository.findAll(request);
	   return page;
		
	}
}
