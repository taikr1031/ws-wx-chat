package com.zm.controller;

import com.zm.handler.SystemWebSocketHandler;
import com.zm.model.User;
import com.zm.repository.ParticipantRepository;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {

  private SimpMessagingTemplate messagingTemplate;
  @Autowired
  private ParticipantRepository participantRepository;

  private static final String LOGIN = "/app/chat.login";
  private static final String LOGOUT = "/app/chat.logout";

//  public LoginController() {
//  }
//  @Autowired
//  public LoginController(SimpMessagingTemplate messagingTemplate) {
//	this.messagingTemplate = messagingTemplate;
//  }

  @RequestMapping(value = "login", method = RequestMethod.POST)
  public String login(HttpServletRequest httpRequest, User user) throws ServletException {
	user.setTime(new Date());
	httpRequest.getSession().setAttribute(Constants.SESSION_USERNAME, user);
	SystemWebSocketHandler handler = new SystemWebSocketHandler();
//	handler.sendMessageToUser();

//	messagingTemplate.convertAndSend(LOGIN, user);
//	if (participantRepository.getActiveSessions().containsKey(httpRequest.getSession().getId())) {
//	  messagingTemplate.convertAndSend(LOGOUT, participantRepository.getActiveSessions().get(httpRequest.getSession().getId()));
//	}
	participantRepository.add(httpRequest.getSession().getId(), user);
	return "redirect:/chart";
  }

}