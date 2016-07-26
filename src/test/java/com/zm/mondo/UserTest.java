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
	GenericMongoService mongoService = (GenericMongoServiceImpl)context.getBean("genericMongoService");

	User user = new User();
	user.setId("2");
	user.setName("张三");
	user.setPassword("123456");
	user.setOpenid("111");
	user.setPhone("222");
	user.setCell("222");
	user.setEmail("222@qq.com");
	user.setGender("女");
	user.setTime(new Timestamp(System.currentTimeMillis()));
	Location location = new Location();
	location.setState("中国");
	location.setCity("武汉");
	location.setStreet("宝丰路");
	location.setZip(430064);
	user.setLocation(location);
	Picture picture = new Picture();
	picture.setLarge("/www/img/perry.png");
	picture.setMedium("/www/img/perry.png");
	picture.setThumbnail("/www/img/perry.png");
	user.setPicture(picture);

	mongoService.saveObject(user);
  }
}
