package com.zm.model.message;

public class LastMessage {

  private String originalTime;
  private String time;
  private int timeFrome1970;
  private String content;
  private boolean isFromeMe;

  public String getOriginalTime() {
    return originalTime;
  }

  public void setOriginalTime(String originalTime) {
    this.originalTime = originalTime;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getTimeFrome1970() {
    return timeFrome1970;
  }

  public void setTimeFrome1970(int timeFrome1970) {
    this.timeFrome1970 = timeFrome1970;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isFromeMe() {
    return isFromeMe;
  }

  public void setFromeMe(boolean fromeMe) {
    isFromeMe = fromeMe;
  }
}
