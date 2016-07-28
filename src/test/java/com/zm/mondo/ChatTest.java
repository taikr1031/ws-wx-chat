package com.zm.mondo;

import com.zm.model.chat.Chat;
import com.zm.model.chat.Message;
import com.zm.model.user.Location;
import com.zm.model.user.Picture;
import com.zm.model.user.User;
import com.zm.mongo.core.GenericMongoService;
import com.zm.mongo.core.GenericMongoServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.sql.Timestamp;

public class ChatTest {

  public static void main(String[] args) {
	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-mongodb-test.xml"});
	GenericMongoService mongoService = (GenericMongoServiceImpl) context.getBean("genericMongoService");

	createChat(mongoService, "1", "2", 1);
	createChat(mongoService, "1", "3", 2);
	createChat(mongoService, "1", "4", 3);

	createChat(mongoService, "2", "1", 1);
	createChat(mongoService, "2", "3", 2);
	createChat(mongoService, "2", "4", 3);
  }

  private static void createChat(GenericMongoService mongoService, String own, String friend, int num) {
	Chat chat = new Chat();
	chat.setId(own + "-" + friend);
	chat.setOwnId("id" + own);
	chat.setOwnPic("/www/img/" + own + ".png");
	chat.setOwnCode("code" + own);
	chat.setOwnName("name" + own);
	chat.setFriendId("id" + friend);
	chat.setFriendPic("/www/img/" + friend + ".png");
	chat.setFriendCode("code" + friend);
	chat.setFriendName("name" + friend);
	chat.setNoReadMessages(num);
	chat.setShowHints(false);
	chat.setOriginalTime(new Timestamp(System.currentTimeMillis()));

	Message msg1 = new Message();
	msg1.setId("m" + own);
	msg1.setContent("/www/img/" + own + ".png");
	msg1.setPic("pic" + own);
	msg1.setFromeMe(true);
	chat.addMessage(msg1);

	Message msg2 = new Message();
	msg2.setId("m" + friend);
	msg2.setContent("content" + friend);
	msg2.setPic("/www/img/" + friend + ".png");
	msg2.setFromeMe(false);
	chat.setLastMessage(msg2);
	chat.addMessage(msg2);

	mongoService.saveObject(chat);
  }
}
