package com.dataextraction.persistence;

import java.util.List;

import com.dataextraction.model.OlympicData;
import com.dataextraction.model.User;

public interface IDataExtractionDao {
	public List<User> getUsers();

	public User login(String login, String password);

	void insertUser(User user);

	User getUserDetails(String userName);

	List<OlympicData> getOlympicData();

}
