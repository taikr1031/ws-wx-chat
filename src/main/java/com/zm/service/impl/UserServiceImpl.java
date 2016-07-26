package com.zm.service.impl;

import com.zm.model.user.Location;
import com.zm.model.user.Picture;
import com.zm.model.user.User;
import com.zm.mongo.core.GenericMongoService;
import com.zm.mongo.core.GenericMongoServiceImpl;
import com.zm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service("userService")
public class UserServiceImpl extends GenericMongoServiceImpl<User> implements UserService {

}
