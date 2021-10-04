
package com.mobile.application.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.mobile.application.model.User;
import com.mobile.application.repository.UserRepository;


@RunWith(MockitoJUnitRunner.Silent.class)

 public class LoginControllerTests {
  
 @Mock
 private UserRepository userRepository;
 @InjectMocks 
 LoginController loginController;
 
 User user=new User("a","A",(long) 1231456,"abd","User");

  
  
  @Test
  public void loginUser() {
  
	  Mockito.when(userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword())).thenReturn(user);
	  
	 
  }
  
 
  }
 