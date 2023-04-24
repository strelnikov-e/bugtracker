package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.UserRepository;
import com.strelnikov.bugtracker.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findByUsername(String username) {
		
		return userRepository.getReferenceById(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
}
