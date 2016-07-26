package com.zm.mongo.core;

import com.mongodb.WriteResult;
import com.zm.util.DefectHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@Service("genericMongoService")
public class GenericMongoServiceImpl<T extends Serializable> implements GenericMongoService<T> {

  private Class<T> entityClass;

  @Resource
  private MongoTemplate mongoTemplate;

  public GenericMongoServiceImpl() {
	entityClass = DefectHelper.getSuperClassGenericType(getClass());
  }

  @Override
  public List<T> getAllObjects() {
	return mongoTemplate.findAll(entityClass);
  }

  @Override
  public void saveObject(T tree) {
	mongoTemplate.insert(tree);
  }

  @Override
  public T getObject(String id) {
	return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), entityClass);
  }

  @Override
  public WriteResult updateObject(String id, String name) {
	return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), Update.update("name", name), entityClass);
  }

  @Override
  public void deleteObject(String id) {
	mongoTemplate.remove(new Query(Criteria.where("id").is(id)), entityClass);
  }

  @Override
  public void createCollection() {
	if (!mongoTemplate.collectionExists(entityClass)) {
	  mongoTemplate.createCollection(entityClass);
	}
  }

  @Override
  public void dropCollection() {
	if (mongoTemplate.collectionExists(entityClass)) {
	  mongoTemplate.dropCollection(entityClass);
	}
  }

  public MongoTemplate getMongoTemplate() {
	return mongoTemplate;
  }
}
