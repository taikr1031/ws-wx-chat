package com.zm.service;

import com.zm.model.chat.Chat;
import com.zm.mongo.core.GenericMongoService;

import java.util.List;

public interface ChatService extends GenericMongoService<Chat> {

  List<Chat> queryAllChatByUserCode(String ownCode);

//  List<Chat> queryAllChatWithoutOwn(String ownCode);

  Chat getChatByFriend(String ownId, String friendId);

  void sendMessage(String ownId, String friendId, String message);

  void save(String chatId, String friendCode, String msg);
}
