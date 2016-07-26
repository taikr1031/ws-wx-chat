package com.zm.controller;

import com.zm.model.user.User;
import com.zm.service.LoginService;
import com.zm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping("/getAllUser")
  public List<User> getAllUser() {
	return userService.getAllObjects();
  }
}
