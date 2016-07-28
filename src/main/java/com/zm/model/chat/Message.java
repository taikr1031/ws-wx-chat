package com.zm.model.chat;

import com.zm.model.GenericObject;

import java.sql.Timestamp;

public class Message extends GenericObject {

  private boolean fromeMe;
  private String content;
  private String pic;
  private String type;
  private String mediaId;
  private Timestamp time;

  public boolean isFromeMe() {
	return fromeMe;
  }

  public void setFromeMe(boolean fromeMe) {
	this.fromeMe = fromeMe;
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

  public String getPic() {
	return pic;
  }

  public void setPic(String pic) {
	this.pic = pic;
  }

  public String getType() {
	return type;
  }

  public void setType(String type) {
	this.type = type;
  }

  public String getMediaId() {
	return mediaId;
  }

  public void setMediaId(String mediaId) {
	this.mediaId = mediaId;
  }
}
