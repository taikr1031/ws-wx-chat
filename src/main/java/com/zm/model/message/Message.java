package com.zm.model.message;

import java.sql.Timestamp;

public class Message {

  private String isFromeMe;
  private String content;
  private Timestamp time;

  public String getIsFromeMe() {
	return isFromeMe;
  }

  public void setIsFromeMe(String isFromeMe) {
	this.isFromeMe = isFromeMe;
  }

  public String getContent() {
	return content;
  }

  public void setContent(String content) {
	this.content = content;
  }

  public Timestamp getTime() {
	return time;
  }

  public void setTime(Timestamp time) {
	this.time = time;
  }
}
