package com.dataextraction.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.dataextraction.model.Contact;
import com.dataextraction.model.OlympicData;
import com.dataextraction.model.User;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Component("dataExtractionDao")
public class DataExtractionDaoImpl implements IDataExtractionDao {

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

	@Override
	public void insertUser(User user) {

		MongoCollection<Document> col = db.getCollection("users");
		Document doc = new Document();

		// BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

		// doc.append("_id", user.getId());

		doc.append("firstName", user.getFirstName());

		doc.append("lastName", user.getLastName());
		doc.append("password", user.getPassword());
		doc.append("userName", user.getUserName());
		doc.append("dateOfBirth", user.getDateOfBirth());

		Document contactDoc = new Document();
		contactDoc.append("address", user.getContact().getAddress());
		contactDoc.append("landline", user.getContact().getLandline());
		contactDoc.append("mobile", user.getContact().getMobile());
		doc.append("contact", contactDoc);
		// doc.append("isEmployee", user.isEmployee());

		col.insertOne(doc);
	}

	@Override
	public List<User> getUsers() {
		MongoCollection<Document> col = db.getCollection("users");

		// col.findOneAndDelete(Filters.eq("_id", 4));

		FindIterable<Document> it = col.find();

		MongoCursor<Document> cursor = it.iterator();

		System.out.println("Result   ");

		List<User> list = new ArrayList<>();
		Document doc = null;
		User user = null;
		while (cursor.hasNext()) {
			user = new User();
			doc = cursor.next();
			user = copyDocToUser(doc);

			list.add(user);

		}
		System.out.println("Result   " + list);

		return list;
	}

	@Override
	public User getUserDetails(String userName) {
		MongoCollection<Document> col = db.getCollection("users");

		// col.findOneAndDelete(Filters.eq("_id", 4));

		FindIterable<Document> it = col.find(Filters.eq("userName", userName));

		MongoCursor<Document> cursor = it.iterator();

		System.out.println("Result   ");

		Document doc = null;
		User user = null;
		while (cursor.hasNext()) {
			user = new User();
			doc = cursor.next();
			user = copyDocToUser(doc);
		}

		return user;
	}

	@Override
	public User login(String login, String password) {
		MongoCollection<Document> col = db.getCollection("users");
		Document myDoc = col.find(Filters.and(Filters.eq("userName", login), Filters.eq("password", password))).first();
		if (myDoc != null) {
			System.out.println(myDoc);
			return copyDocToUser(myDoc);
		}
		return null;
	}

	@Override
	public List<OlympicData> getOlympicData() {
		MongoCollection<Document> col = db.getCollection("olympicWiners");

		// col.findOneAndDelete(Filters.eq("_id", 4));

		FindIterable<Document> it = col.find();

		MongoCursor<Document> cursor = it.iterator();

		List<OlympicData> list = new ArrayList<>();
		Document doc = null;
		OlympicData olympicData = null;
		while (cursor.hasNext()) {
			olympicData = new OlympicData();
			doc = cursor.next();
			olympicData = copyDocToolympicData(doc);

			list.add(olympicData);

		}

		return list;

	}

	private OlympicData copyDocToolympicData(Document doc) {
		OlympicData olympicData = new OlympicData();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		olympicData.setAthlete((String) doc.get("athlete"));
		olympicData.setAge((Integer) doc.get("age"));
		olympicData.setCountry((String) doc.get("country"));
		olympicData.setYear((Integer) doc.get("year"));
		try {
			olympicData.setDate(sdf.parse((String) doc.get("date")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		olympicData.setSport((String) doc.get("sport"));

		olympicData.setGold((Integer) doc.get("gold"));
		olympicData.setSilver((Integer) doc.get("silver"));
		olympicData.setBronze((Integer) doc.get("bronze"));
		olympicData.setTotal((Integer) doc.get("total"));
		return olympicData;
	}

	private User copyDocToUser(Document doc) {
		User user = new User();

		user.setFirstName((String) doc.get("firstName"));
		user.setLastName((String) doc.get("lastName"));
		user.setPassword((String) doc.get("password"));
		user.setUserName((String) doc.get("userName"));
		Document conDoc = (Document) doc.get("contact");
		Contact contact = new Contact();
		if (conDoc != null) {
			contact.setAddress(conDoc.getString("address"));
			contact.setLandline(conDoc.getString("landline"));
			contact.setMobile(conDoc.getString("mobile"));
			user.setContact(contact);
		}
		return user;
	}

}
