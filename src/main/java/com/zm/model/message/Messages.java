package com.zm.model.message;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document
public class Messages implements Serializable {

  @Id
  private String id;
  private String name;
  private String pic;
  private String openid;
  private int noReadMessages;
  private int isTop;
  private boolean showHints;
  private LastMessage lastMessage;
  private List<Message> message;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public int getNoReadMessages() {
    return noReadMessages;
  }

  public void setNoReadMessages(int noReadMessages) {
    this.noReadMessages = noReadMessages;
  }

  public int getIsTop() {
    return isTop;
  }

  public void setIsTop(int isTop) {
    this.isTop = isTop;
  }

  public boolean isShowHints() {
    return showHints;
  }

  public void setShowHints(boolean showHints) {
    this.showHints = showHints;
  }

  public LastMessage getLastMessage() {
    return lastMessage;
  }

  public void setLastMessage(LastMessage lastMessage) {
    this.lastMessage = lastMessage;
  }

  public List<Message> getMessage() {
    return message;
  }

  public void setMessage(List<Message> message) {
    this.message = message;
  }
}
