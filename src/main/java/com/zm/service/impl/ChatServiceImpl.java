package com.zm.service.impl;

import com.zm.model.chat.Chat;
import com.zm.model.chat.Message;
import com.zm.mongo.core.GenericMongoServiceImpl;
import com.zm.service.ChatService;
import com.zm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatService")
public class ChatServiceImpl extends GenericMongoServiceImpl<Chat> implements ChatService {

  @Override
  public List<Chat> queryAllChatByUserId(String userId) {
	Query query = new Query();
	Criteria c = new Criteria().orOperator(Criteria.where("auserId").is(userId), Criteria.where("buserId").is(userId));
	query.addCriteria(c);
	query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "originalTime")));
	List<Chat> chats = getMongoTemplate().find(query, Chat.class);
	for (Chat chat : chats) {
	  int num = getNoReadMsgNum(chat.getId(), userId);
	  chat.setNoReadMsgNum(num);
	}
	return chats;
  }

  public int getNoReadMsgNum(String chatId, String userId) {
	Query query = new Query();
	query.addCriteria(Criteria.where("id").is(chatId).and("messages.userId").is(userId).and("read").exists(false));
	int num = (int) getMongoTemplate().count(query, Chat.class);
	System.out.println(chatId + "-" + userId + "=" + num);
	return num;
  }

  @Override
  public Chat getChatByFriend(String ownId, String friendId) {
	return null;
  }

  @Override
  public void sendMessage(String ownId, String friendId, String message) {

  }

  @Override
  public void save(String chatId, Message message) {
	Query query = new Query(Criteria.where("id").is(chatId));
	Update update = new Update().addToSet("messages", message);
	this.getMongoTemplate().upsert(query, update, Chat.class);
  }

  @Override
  public void update(String chatId, String userId) {
	Query query = new Query();
	query.addCriteria(Criteria.where("id").is(chatId).and("messages").elemMatch(Criteria.where("userId").is(userId)));
	Update update = new Update().set("read", true);
	this.getMongoTemplate().updateMulti(query, update, Chat.class);
  }

  private boolean isExistFriendChat(String openid, List<Chat> chats) {
	for (Chat chat : chats) {
	  if (chat.getBuserCode().equals(openid)) {
		return true;
	  }
	}
	return false;
  }
}
