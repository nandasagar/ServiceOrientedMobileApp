package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.UserDto;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.User;
import com.mobile.application.service.UserServices;

/**
 * Admin Login Controller
 * 
 * @author Nanda sagar
 *
 */
//@SessionAttributes("Admin")
@Controller
@ResponseBody
@RequestMapping("AdminLogin")
public class AdminLoginController {

	/**
	 * Admin Session Setup
	 * 
	 * @return
	 */
	@ModelAttribute("Admin")
	public User setUp() {
		return new User();
	}

	@Autowired
	private UserServices userService;
	@Autowired
	private ModelMapper modelMapper;
	Logger log = LoggerFactory.getLogger(AdminLoginController.class);

	/**
	 * creating post mapping that post the new user detail in the database
	 * 
	 * @param users
	 * @return
	 */
	@PostMapping("/")
	private UserDto saveAdmin(@RequestBody UserDto users) {
		String email = users.getEmail();
		User userEntity = modelMapper.map(users, User.class);
		User user = null;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex) && users.getRolename().equals("Admin")) {
			user = userService.saveUser(userEntity);
			if (Objects.isNull(users.getId())) {
				log.error(" Error : Enter correct User Details");
				throw new UserNotfoundException("Error while Admin Registration..!");
			}
		}
		log.info("AdminLoginController saveAdmin() response{}", user);
		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * creating put mapping that updates the Admin details
	 * 
	 * @param users
	 * @return
	 */
	@PutMapping("/")
	private UserDto updateAdmin(@RequestBody UserDto users) {
		String email = users.getEmail();
		User userEntity = modelMapper.map(users, User.class);
		User user = null;
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex)) {
			user = userService.saveUser(userEntity);
		}
		if (Objects.isNull(user)) {
			log.error(" Error : Enter correct User Details");
			throw new UserNotfoundException("Error while Admin Updation..!");
		}
		log.info("AdminLoginController updateAdmin() response{}", user);
		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * Validates Admin Credentials
	 * 
	 * @param user
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@GetMapping("/validateAdmin/{email}/{password}")
	public UserDto LoginAdmin(@PathVariable String email, @PathVariable String password) {
		User userList = userService.findByEmailAndPassword(email, password);
		if (Objects.isNull(userList)) {
			log.error(" Error : Enter correct User Details");
			throw new UserNotfoundException("email or Password is incorrect for this email id " + email);
		}
		if (userList.getRolename().equals("Admin")) {
			log.info("AdminLoginController LoginAdmin() response{}", userList);		
			return modelMapper.map(userList, UserDto.class);
		} else
			log.error(" Error : Enter correct User Details");
			throw new UserNotfoundException("email or Password is incorrect for this email id " + email);
	}
}