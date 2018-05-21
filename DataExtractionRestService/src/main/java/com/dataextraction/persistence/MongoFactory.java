package com.dataextraction.persistence;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Component("mongoFactory")
public class MongoFactory {

	private MongoClient mongoClient;
	private MongoDatabase db;

	@PostConstruct
	public void init() {
		mongoClient = new MongoClient("localhost", 27017);

		db = mongoClient.getDatabase("mydb");

	}

	@PreDestroy
	public void destoy() {
		mongoClient.close();

	}

	public MongoDatabase getMongoDB() {
		return db;
	}
}
