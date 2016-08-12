package com.zm.mondo;

import com.zm.model.chat.Chat;
import com.zm.model.chat.Message;
import com.zm.mongo.core.GenericMongoService;
import com.zm.mongo.core.GenericMongoServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;

public class ChatInitDB {

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

	  createChat(mongoService, "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", "3", "LJ", "oMPxav8EjT7cotajZ7_YSisGbFtc", 33);
	  Thread.sleep(1000);
	  createChat(mongoService, "2", "YJ", "oMPxav6aC_TuPncPkgHhE998bboA", "4", "WY", "code4", 44);
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
  }

  private static void createChat(GenericMongoService mongoService, String auserId, String auserName, String auserCode, String buserId, String buserName, String buserCode, int num) {
	Chat chat = new Chat();
	chat.setId(auserId, buserId);
	chat.setAuserId(auserId);
	chat.setBuserId(buserId);
	chat.setAuserPic("/www/img/" + auserId + ".png");
	chat.setBuserPic("/www/img/" + buserId + ".png");
	chat.setAuserCode(auserCode);
	chat.setBuserCode(buserCode);
	chat.setAuserName(auserName);
	chat.setBuserName(buserName);
	chat.setAuserTimeFrome1970(new Date().getTime());
	chat.setBuserTimeFrome1970(new Date().getTime());
	chat.setAuserShowHints(false);
	chat.setBuserShowHints(false);
	chat.setAuserOriginalTime(new Timestamp(System.currentTimeMillis()));
	chat.setBuserOriginalTime(new Timestamp(System.currentTimeMillis()));

	Message msg1 = new Message();
	msg1.setId(chat.getId());
	msg1.setUserId(auserId);
	msg1.setContent("content" + 1);
	msg1.setPic("/www/img/" + auserId + ".png");
	msg1.setType("TEXT");
	msg1.setMediaId("mdeia" + auserId);
	msg1.setRead(false);
	chat.addMessage(msg1);

	Message msg2 = new Message();
	msg2.setId(chat.getId());
	msg2.setUserId(buserId);
	msg2.setContent("content" + 2);
	msg2.setPic("/www/img/" + buserId + ".png");
	msg2.setType("TEXT");
	msg2.setMediaId("mdeia" + buserId);
	msg2.setRead(false);
	chat.addMessage(msg2);

//	Message msg3 = new Message();
//	msg3.setId(chat.getId());
//	msg3.setUserId(auserId);
//	msg3.setContent("content" + 3);
//	msg3.setPic("/www/img/" + auserId + ".png");
//	msg3.setType("TEXT");
//	msg3.setMediaId("mdeia" + auserId);
//	msg3.setRead(false);
//	chat.addMessage(msg3);
//
//	Message msg4 = new Message();
//	msg4.setId(chat.getId());
//	msg4.setUserId(buserId);
//	msg4.setContent("content" + 4);
//	msg4.setPic("/www/img/" + buserId + ".png");
//	msg4.setType("TEXT");
//	msg4.setMediaId("mdeia" + buserId);
//	msg4.setRead(true);
//	chat.setLastMessage(msg4);
//	chat.addMessage(msg4);

	mongoService.saveObject(chat);
  }
}
