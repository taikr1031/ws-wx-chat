package com.zm.controller;

import com.zm.model.user.User;
import com.zm.repository.ParticipantRepository;
import com.zm.service.LoginService;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController {

  @Autowired
  private ParticipantRepository participantRepository;

  @Autowired
  private LoginService loginService;

  private static final String LOGIN = "/app/chat.login";
  private static final String LOGOUT = "/app/chat.logout";

  @RequestMapping("/login/{username}/{openid}")
  public void loginSession(@PathVariable("username") String username, @PathVariable("openid") String openid, HttpServletRequest httpRequest) throws ServletException {
	User user = loginService.login(username, openid);
	user.setTime(new Date());
	httpRequest.getSession().setAttribute(Constants.SESSION_USERNAME, user);
	participantRepository.add(openid, user);
  }

  @RequestMapping("/isLogin/{openid}")
  public boolean isLoginSession(@PathVariable("openid") String openid, HttpServletRequest httpRequest) throws ServletException {
	if (httpRequest.getSession().getAttribute(Constants.SESSION_USERNAME) == null) {
	  return false;
	}
	if (!((User) httpRequest.getSession().getAttribute(Constants.SESSION_USERNAME)).getOpenid().equals(openid)) {
	  return false;
	}
	return true;
  }

}
