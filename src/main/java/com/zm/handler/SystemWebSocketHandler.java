package com.zm.handler;

import java.io.IOException;
import java.util.ArrayList;

import com.zm.service.WebSocketService;
import com.zm.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

  private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

  @Autowired
  private WebSocketService webSocketService;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	System.out.println("ConnectionEstablished");
	int num = webSocketService.getUnReadNews((String) session.getAttributes().get("id"));
	users.add(session);
	session.sendMessage(new TextMessage(num + ""));
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
	sendMessageToUsers((TextMessage) message);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
	if (session.isOpen()) {
	  session.close();
	}
	users.remove(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
	users.remove(session);
  }

  @Override
  public boolean supportsPartialMessages() {
	return false;
  }

  /**
   * 给所有在线用户发送消息
   *
   * @param message
   */
  public void sendMessageToUsers(TextMessage message) {
	for (WebSocketSession user : users) {
	  try {
		if (user.isOpen()) {
		  user.sendMessage(message);
		}
	  } catch (IOException e) {
		e.printStackTrace();
	  }
	}
  }

  /**
   * 给某个用户发送消息
   *
   * @param userName
   * @param message
   */
  public void sendMessageToUser(String userName, TextMessage message) {
	for (WebSocketSession user : users) {
	  if (user.getAttributes().get(Constants.WEBSOCKET_USERNAME).equals(userName)) {
		try {
		  if (user.isOpen()) {
			user.sendMessage(message);
		  }
		} catch (IOException e) {
		  e.printStackTrace();
		}
		break;
	  }
	}
  }

}