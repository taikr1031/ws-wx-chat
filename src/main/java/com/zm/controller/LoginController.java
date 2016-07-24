package com.zm.controller;

import com.zm.model.User;
import com.zm.repository.ParticipantRepository;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {

  @Autowired
  private ParticipantRepository participantRepository;

  private static final String LOGIN = "/app/chat.login";
  private static final String LOGOUT = "/app/chat.logout";

  @RequestMapping("/loginSession/{openid}")
  public void loginSession(@PathVariable("openid") String openid, HttpServletRequest httpRequest) throws ServletException {
	User user = new User();
	user.setUsername(openid);
	user.setTime(new Date());
	httpRequest.getSession().setAttribute(Constants.SESSION_USERNAME, user);
	participantRepository.add(httpRequest.getSession().getId(), user);
  }

  @RequestMapping("/isLoginSession/{openid}")
  public boolean isLoginSession(@PathVariable("openid") String openid, HttpServletRequest httpRequest) throws ServletException {
	if(httpRequest.getSession().getAttribute(Constants.SESSION_USERNAME) == null) {
	  return false;
	}
	if(!((User)httpRequest.getSession().getAttribute(Constants.SESSION_USERNAME)).getUsername().equals(openid)) {
	  return false;
	}
	return true;
  }

}
