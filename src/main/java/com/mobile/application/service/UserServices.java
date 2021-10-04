package com.mobile.application.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.User;
import com.mobile.application.repository.UserRepository;

@Service
@Transactional
public class UserServices {
	@Autowired
	UserRepository userRepository;

	public List<User> getAllUsers() {
		System.out.println((List<User>) userRepository.findAll());
		return (List<User>) userRepository.findAll();
	}

	public Page<User> getAllUser(Pageable pageable) {
		return (Page<User>) userRepository.findAll(pageable);

	}

	public User findById(Integer id) {
		return userRepository.findById(id);
	}

	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}
}
