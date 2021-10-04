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
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.UserDto;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.User;
import com.mobile.application.service.UserServices;

@Controller
@ResponseBody
public class LoginController {

	/**
	 * Session Instantiation
	 * 
	 * @return
	 */
	@ModelAttribute("User")
	public User setUp() {
		return new User();
	}

	@Autowired
	private UserServices userService;
	
	@Autowired
	private ModelMapper modelMapper;
	Logger log = LoggerFactory.getLogger(LoginController.class);
	/**
	 * creating post mapping that post the new user detail in the database
	 * 
	 * @param users
	 * @return
	 */
	@PostMapping("/saveUsers")
	private UserDto saveUser(@RequestBody UserDto users) {
		User user = modelMapper.map(users, User.class);
		User userEntity = userService.saveUser(user);
		if (Objects.isNull(users.getId())) {
			log.error(" Error : Enter correct User Details");
			throw new UserNotfoundException("id: " + users.getId());
		}
		log.info("LoginController saveUser() response{}", userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}

	/**
	 * creating put mapping that updates the user detail
	 * 
	 * @param users
	 * @return
	 */

	@PutMapping("/updateUsers")
	private UserDto updateUser(@RequestBody UserDto users) {
		String email = users.getEmail();
		User user = null;
		User userEntity = modelMapper.map(users, User.class);
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		if (email.matches(regex)) {
			user = userService.saveUser(userEntity);
		}
		if (Objects.isNull(user)) {
			log.error(" Error in UpdatUser Enter correct User Details");
			throw new UserNotfoundException("email: " + email);
		}
		log.info("LoginController updateUser() response{}", user);

		return modelMapper.map(user, UserDto.class);
	}

	/**
	 * Validates on users login
	 * 
	 * @param user
	 * @param model
	 * @param request
	 * @return
	 * 
	 */
	@GetMapping("/validateUser/{email}/{password}")
	public UserDto loginUser(@PathVariable String email, @PathVariable String password) {
		User userList = null;
		userList = userService.findByEmailAndPassword(email, password);
		if (Objects.isNull(userList)) {
			log.error(" Error : Enter correct User Details");
			throw new UserNotfoundException("email or Password is incorrect for this email id " + email);
		}
		log.info("LoginController validateuser() response{}", userList);

		return modelMapper.map(userList, UserDto.class);
	}
}
