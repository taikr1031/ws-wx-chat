package com.zm.controller;

import com.zm.model.chat.Chat;
import com.zm.model.user.User;
import com.zm.repository.ParticipantRepository;
import com.zm.service.ChatService;
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
import java.util.List;

@Controller
@RequestMapping("chat")
public class ChartController {

  @Autowired
  private ChatService chatService;

  @RequestMapping("queryChat")
  public List<Chat> queryChat(HttpServletRequest request) {
	List<Chat> chats = null;
	try {
	  chats = chatService.getAllObjects();
//	  User loginUser = (User) request.getSession().getAttribute(Constants.SESSION_USERNAME);
//	  if (loginUser != null) {
//		String ownCode = loginUser.getCode();
//		chats = chatService.queryAllChatWithoutOwn(ownCode);
//	  }
	} catch (Exception e) {
	  e.printStackTrace();
	}
	return chats;
  }
}
