package com.zm.mondo;

import com.zm.model.chat.Chat;
import com.zm.model.chat.Message;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

public class DaoTest {

//  @Test
  public void testQuery() {
	Query query = new Query();
	query.addCriteria(Criteria.where("id").is("1-2").and("messages.userId").is("1").and("read").exists(false));
	int num = (int) getMongoTemplate().count(query, Chat.class);
	System.out.println(num);
	Assert.notNull(num);
  }

  @Test
  public void testUpdate() {
	String chatId = "1-2";
	String userId = "2";
	Query query = new Query();
//	query.addCriteria(Criteria.where("id").is(chatId).and("messages").elemMatch(Criteria.where("userId").is(userId)));
	query.addCriteria(Criteria.where("id").is(chatId).and("userId").is(userId));
	Update update = new Update().set("read", true);
	this.getMongoTemplate().updateMulti(query, update, Message.class);

//	Query query1 = new Query();
//	query1.addCriteria(Criteria.where("id").is(chatId).and("messages.userId").is(userId).and("read").is(true));
//	Chat chat = getMongoTemplate().findOne(query1, Chat.class);
//	long count = getMongoTemplate().count(query1, Chat.class);
//	System.out.println("count: " + count);
//	Assert.notNull(count, "error");
  }

  public MongoTemplate getMongoTemplate() {
	String configLocations = "spring-mongodb.xml";
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);
	MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
	return mongoTemplate;
  }
}
