package com.zm.controller;

import com.zm.model.chat.Chat;
import com.zm.model.user.User;
import com.zm.service.ChatService;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("chat")
public class ChatController {

  private String openid;
  private String msg;

  @Autowired
  private ChatService chatService;

  @RequestMapping("queryChat")
  public List<Chat> queryChat(HttpServletRequest request) {
	List<Chat> chats = null;
	try {
	  User user = (User) request.getSession().getAttribute(Constants.SESSION_USERNAME);
	  if (user == null) {
		return new ArrayList<Chat>();
	  }
	  chats = chatService.queryAllChatByUserCode(user.getCode());
	} catch (Exception e) {
	  e.printStackTrace();
	}
	return chats;
  }

  @RequestMapping("save")
  public void save(@RequestBody String msg, HttpServletRequest request) {
	System.out.print(request.getQueryString());
	String ownCode = ((User) request.getSession().getAttribute(Constants.SESSION_USERNAME)).getCode();
	String[] params = msg.split("&");
	this.chatService.save(params[0].split("=")[1], params[1].split("=")[1], params[2].split("=")[1]);
  }

  public String getOpenid() {
	return openid;
  }

  public void setOpenid(String openid) {
	this.openid = openid;
  }

  public String getMsg() {
	return msg;
  }

  public void setMsg(String msg) {
	this.msg = msg;
  }
}
