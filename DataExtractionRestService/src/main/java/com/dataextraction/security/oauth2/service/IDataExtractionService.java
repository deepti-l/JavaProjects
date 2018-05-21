package com.dataextraction.security.oauth2.service;

import java.util.List;

import com.dataextraction.model.OlympicData;
import com.dataextraction.model.User;

public interface IDataExtractionService {

	public List<User> getUsers();

	void insertUser(User user);

	User getUserDetails(String userName);

	List<OlympicData> getOlympicData();
}
