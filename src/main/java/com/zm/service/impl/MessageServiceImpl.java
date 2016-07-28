package com.zm.service.impl;


import com.zm.model.chat.Message;
import com.zm.mongo.core.GenericMongoServiceImpl;
import com.zm.service.MessageService;
import org.springframework.stereotype.Service;

@Service("messageService")
public class MessageServiceImpl extends GenericMongoServiceImpl<Message> implements MessageService{
}
