package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.configuration.PlainAuthentication;
import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.exception.AccessForbiddenException;
import com.strelnikov.bugtracker.exception.UserAlreadyExistsException;
import com.strelnikov.bugtracker.exception.UserNotFoundException;
import com.strelnikov.bugtracker.repository.UserRepository;
import com.strelnikov.bugtracker.repository.UserRoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private UserRoleRepository userRoleRepository;
	
	public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	private User getCurrentUser() {
		final var userId = ((PlainAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
		return userRepository.findById(userId).orElseThrow();
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

		if (!Objects.equals(user.getId(), getCurrentUser().getId())) {
			throw new AccessForbiddenException();
		}
		user.setFirstName(requestUser.getFirstName());
		user.setLastName(requestUser.getLastName());
		user.setCompanyName(requestUser.getCompanyName());
		user.setEmail(requestUser.getEmail());
		user.setPassword(requestUser.getPassword());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteByUsername(String username) {
		Long userId = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(username))
				.getId();

		userRoleRepository.deleteAllByUserId(userId);
		userRepository.deleteByUsername(username);
	}
}
