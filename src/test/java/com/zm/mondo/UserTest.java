package com.zm.mondo;

import com.zm.model.user.Location;
import com.zm.model.user.Picture;
import com.zm.model.user.User;
import com.zm.mongo.core.GenericMongoService;
import com.zm.mongo.core.GenericMongoServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;

public class UserTest {

  public static void main(String[] args) {
	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-mongodb-test.xml"});
	GenericMongoService mongoService = (GenericMongoServiceImpl) context.getBean("genericMongoService");

	createUser(mongoService, "1");
	createUser(mongoService, "2");
	createUser(mongoService, "3");
	createUser(mongoService, "4");
  }

  private static void createUser(GenericMongoService mongoService, String own) {
	User user = new User();
	user.setId(own);
	user.setName("name" + own);
	user.setPassword("password" + own);
	user.setCode("code" + own);
	user.setPhone("phone" + own);
	user.setCell("cell" + own);
	user.setEmail(own + "@qq.com");
	user.setGender("女");
	user.setTime(new Timestamp(System.currentTimeMillis()));
	Location location = new Location();
	location.setState("中国");
	location.setCity("武汉");
	location.setStreet("宝丰路");
	location.setZip(430064);
	user.setLocation(location);
	Picture picture = new Picture();
	picture.setLarge("/www/img/" + own + ".png");
	picture.setMedium("/www/img/" + own + ".png");
	picture.setThumbnail("/www/img/" + own + ".png");
	user.setPicture(picture);

	mongoService.saveObject(user);
  }
}
