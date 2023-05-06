package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.repository.UserRepository;
import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.exception.UserAlreadyExistsException;
import com.strelnikov.bugtracker.exception.UserNotFoundException;
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
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
		return user;
	}

	@Override
	public List<User> findAll(String username) {
		return userRepository.findByUsernameContaining(username);
	}

	@Override
	public User save(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new UserAlreadyExistsException(user.getUsername());
		}
		return userRepository.save(user);
	}

	@Override
	public User update(String username, User requestUser) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

		user.setFirstName(requestUser.getFirstName());
		user.setLastName(requestUser.getLastName());
		user.setCompanyName(requestUser.getCompanyName());
		user.setEmail(requestUser.getEmail());
		user.setPassword(requestUser.getPassword());
		return userRepository.save(user);
	}

	@Override
	public void deleteByUsername(String username) {
		if (!userRepository.existsByUsername(username)) {
			throw new UserNotFoundException(username);
		}
		userRepository.deleteByUsername(username);
	}
}
