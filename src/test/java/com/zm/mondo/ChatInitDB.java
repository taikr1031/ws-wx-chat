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
//	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "2", "YJ", "CODE2", 22);
//	  Thread.sleep(1000);
//	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "3", "LJ", "CODE3", 33);
//	  Thread.sleep(1000);
//	  createChat(mongoService, "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", "4", "ZZL", "oMPxav7E8tYZADIRz58AEcez-RAo", 44);
//	  Thread.sleep(1000);
//
//	  createChat(mongoService, "4", "ZZL", "oMPxav7E8tYZADIRz58AEcez-RAo", "1", "ZM", "oMPxav8gQa7VgRFjILtzRX_lhymE", 11);
//	  Thread.sleep(1000);
//	  createChat(mongoService, "4", "ZZL", "oMPxav7E8tYZADIRz58AEcez-RAo", "3", "LJ", "CODE3", 33);
//	  Thread.sleep(1000);
//	  createChat(mongoService, "4", "ZZL", "oMPxav7E8tYZADIRz58AEcez-RAo", "2", "YJ", "CODE2", 44);


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
	msg1.setUserId("1");
	msg1.setContent("content" + auserId);
	msg1.setPic("/www/img/" + auserId + ".png");
	msg1.setType("TEXT");
	msg1.setMediaId("mdeia" + auserId);
	msg1.setRead(false);
	chat.addMessage(msg1);

	Message msg2 = new Message();
	msg2.setId(chat.getId());
	msg2.setUserId("2");
	msg2.setContent("content" + buserId);
	msg2.setPic("/www/img/" + buserId + ".png");
	msg2.setType("TEXT");
	msg2.setMediaId("mdeia" + buserId);
	msg2.setRead(false);
	chat.setLastMessage(msg2);
	chat.addMessage(msg2);

	mongoService.saveObject(chat);
  }
}
