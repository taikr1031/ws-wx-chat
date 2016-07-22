package com.zm.controller;

import com.zm.model.User;
import com.zm.repository.ParticipantRepository;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Collection;

@Controller
public class ChartController {

  @Autowired
  ParticipantRepository participantRepository;

  @RequestMapping(value = "/chart", method = RequestMethod.GET)
  public String chartPage(HttpServletRequest request, Model model) throws AccessDeniedException {
	if (request.getSession().getAttribute(Constants.SESSION_USERNAME) == null) {
	  throw new AccessDeniedException("login please");
	}
	User user = (User) request.getSession().getAttribute(Constants.SESSION_USERNAME);
	model.addAttribute("participant.username", user.getUsername());
	return "chart";
  }

  @SubscribeMapping("/chat.participants")
  public Collection<User> retrieveParticipants() {
	return participantRepository.getActiveSessions().values();
  }

}
