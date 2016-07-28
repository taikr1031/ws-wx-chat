package com.zm.model.chat;

import com.zm.model.GenericObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Document
public class Chat extends GenericObject {

  private String ownId;
  private String ownPic;
  private String ownCode;
  private String ownName;
  private String friendId;
  private String friendPic;
  private String friendCode;
  private String friendName;
  private int noReadMessages;
  private Timestamp originalTime;
  private boolean top;
  private boolean showHints;
  private Message lastMessage;
  private List<Message> messages;

  public String getOwnId() {
	return ownId;
  }

  public void setOwnId(String ownId) {
	this.ownId = ownId;
  }

  public String getOwnPic() {
	return ownPic;
  }

  public void setOwnPic(String ownPic) {
	this.ownPic = ownPic;
  }

  public String getOwnCode() {
	return ownCode;
  }

  public void setOwnCode(String ownCode) {
	this.ownCode = ownCode;
  }

  public String getFriendId() {
	return friendId;
  }

  public void setFriendId(String friendId) {
	this.friendId = friendId;
  }

  public String getFriendPic() {
	return friendPic;
  }

  public void setFriendPic(String friendPic) {
	this.friendPic = friendPic;
  }

  public String getFriendCode() {
	return friendCode;
  }

  public void setFriendCode(String friendCode) {
	this.friendCode = friendCode;
  }

  public int getNoReadMessages() {
	return noReadMessages;
  }

  public void setNoReadMessages(int noReadMessages) {
	this.noReadMessages = noReadMessages;
  }

  public boolean isTop() {
	return top;
  }

  public void setTop(boolean top) {
	this.top = top;
  }

  public boolean isShowHints() {
	return showHints;
  }

  public void setShowHints(boolean showHints) {
	this.showHints = showHints;
  }

  public String getOwnName() {
	return ownName;
  }

  public void setOwnName(String ownName) {
	this.ownName = ownName;
  }

  public String getFriendName() {
	return friendName;
  }

  public void setFriendName(String friendName) {
	this.friendName = friendName;
  }

  public Timestamp getOriginalTime() {
	return originalTime;
  }

  public void setOriginalTime(Timestamp originalTime) {
	this.originalTime = originalTime;
  }

  public Message getLastMessage() {
	return lastMessage;
  }

  public void setLastMessage(Message lastMessage) {
	this.lastMessage = lastMessage;
  }

  public List<Message> getMessages() {
	return messages;
  }

  public void setMessages(List<Message> messages) {
	this.messages = messages;
  }

  public void addMessage(Message message) {
	if (this.messages == null) {
	  messages = new ArrayList<Message>();
	}
	this.messages.add(message);
  }

  public void removeMessage(Message message) {
	if (this.messages.contains(message)) {
	  this.messages.remove(message);
	}
  }
}
