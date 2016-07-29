package com.zm.mondo;

import com.zm.model.chat.Chat;
import com.zm.model.chat.Message;
import com.zm.mongo.core.GenericMongoService;
import com.zm.mongo.core.GenericMongoServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;

public class ChatTest {

  public static void main(String[] args) {
	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-mongodb-test.xml"});
	GenericMongoService mongoService = (GenericMongoServiceImpl) context.getBean("genericMongoService");

	try {
	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", 22);
	  Thread.sleep(1000);
	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "3", "LJ", "oMPxav8EjT7cotajZ7_YSisGbFtc", 33);
	  Thread.sleep(1000);
	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "4", "WY", "code4", 44);
	  Thread.sleep(1000);

	  createChat(mongoService, "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", 11);
	  Thread.sleep(1000);
	  createChat(mongoService, "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", "3", "LJ", "oMPxav8EjT7cotajZ7_YSisGbFtc", 33);
	  Thread.sleep(1000);
	  createChat(mongoService, "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", "4", "WY", "code4", 44);
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
  }

  private static void createChat(GenericMongoService mongoService, String own, String ownName, String ownCode, String friend, String friendName, String friendCode, int num) {
	Chat chat = new Chat();
	chat.setId(own + "-" + friend);
	chat.setOwnId("id" + own);
	chat.setOwnPic("/www/img/" + own + ".png");
	chat.setOwnCode(ownCode);
	chat.setOwnName(ownName);
	chat.setFriendId("id" + friend);
	chat.setFriendPic("/www/img/" + friend + ".png");
	chat.setFriendCode(friendCode);
	chat.setFriendName(friendName);
	chat.setNoReadMessages(num);
	chat.setTimeFrome1970(new Date().getTime());
	chat.setShowHints(false);
	chat.setOriginalTime(new Timestamp(System.currentTimeMillis()));

	Message msg1 = new Message();
	msg1.setId("m" + own);
	msg1.setContent("content" + own);
	msg1.setPic("/www/img/" + own + ".png");
	msg1.setType("TEXT");
	msg1.setMediaId("mdeia" + own);
	msg1.setFromeMe(true);
	chat.addMessage(msg1);

	Message msg2 = new Message();
	msg2.setId("m" + friend);
	msg2.setContent("content" + friend);
	msg2.setPic("/www/img/" + friend + ".png");
	msg1.setType("TEXT");
	msg1.setMediaId("mdeia" + friend);
	msg2.setFromeMe(false);
	chat.setLastMessage(msg2);
	chat.addMessage(msg2);

	mongoService.saveObject(chat);
  }
}
