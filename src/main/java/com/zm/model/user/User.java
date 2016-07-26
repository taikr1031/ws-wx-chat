package com.zm.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
public class User implements Serializable {

  @Id
  private String id;
  private String name;
  private String password;
  private String openid;
  private String gender;
  private String email;
  private String phone;
  private String cell;
  private Date time;
  private Location location;
  private Picture picture;
//  private Name nameObj;

  public User() {
	super();
  }

  public User(String name) {
	this.name = name;
	time = new Date();
  }

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

  public String getPassword() {
	return password;
  }

  public void setPassword(String password) {
	this.password = password;
  }

  public String getOpenid() {
	return openid;
  }

  public void setOpenid(String openid) {
	this.openid = openid;
  }

  public Date getTime() {
	return time;
  }

  public void setTime(Date time) {
	this.time = time;
  }

  public String getGender() {
	return gender;
  }

  public void setGender(String gender) {
	this.gender = gender;
  }

  public String getEmail() {
	return email;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public String getPhone() {
	return phone;
  }

  public void setPhone(String phone) {
	this.phone = phone;
  }

  public String getCell() {
	return cell;
  }

  public void setCell(String cell) {
	this.cell = cell;
  }

  public Location getLocation() {
	return location;
  }

  public void setLocation(Location location) {
	this.location = location;
  }

  public Picture getPicture() {
	return picture;
  }

  public void setPicture(Picture picture) {
	this.picture = picture;
  }
}
