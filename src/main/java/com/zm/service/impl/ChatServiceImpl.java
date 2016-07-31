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

  @Autowired
  private UserService userService;

  @Override
  public List<Chat> queryAllChatByUserCode(String ownCode) {
	Query query = new Query();
	query.addCriteria(Criteria.where("ownCode").is(ownCode));
	query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "originalTime")));
	List<Chat> chats = getMongoTemplate().find(query, Chat.class);
	return chats;
  }

//  @Override
//  public List<Chat> queryAllChatWithoutOwn(String ownCode) {
//	List<Chat> chats = getMongoTemplate().findAll(Chat.class);
//	List<User> users = userService.getAllObjects();
//	for (User user : users) {
//	  String friendCode = user.getCode();
//	  if (!ownCode.equals(friendCode)) {
//		if (!isExistFriendChat(user.getCode(), chats)) {
//		  Chat chat = new Chat();
//		  chat.setFriendId(user.getId());
//		  chat.setFriendName(user.getName());
//		  chat.setFriendCode(user.getCode());
//		  chat.setFriendPic(user.getPicture().getThumbnail());
//		  chats.add(chat);
//		}
//
//	  }
//	}
//	return chats;
//  }

  @Override
  public Chat getChatByFriend(String ownId, String friendId) {
	return null;
  }

  @Override
  public void sendMessage(String ownId, String friendId, String message) {

  }

  @Override
  public void save(String chatId, String friendCode, String msg) {
	Query query = new Query(Criteria.where("id").is(chatId));
	Message message = new Message();
	message.setFromeMe(true);
	message.setContent(msg);
	message.setPic("/pic");
	message.setType("TEXT");
	Update update = new Update().addToSet("messages", message);
	this.getMongoTemplate().upsert(query, update, Chat.class);
  }

  private boolean isExistFriendChat(String openid, List<Chat> chats) {
	for (Chat chat : chats) {
	  if (chat.getFriendCode().equals(openid)) {
		return true;
	  }
	}
	return false;
  }
}
