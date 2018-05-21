package com.dataextraction.security.oauth2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dataextraction.model.OlympicData;
import com.dataextraction.model.User;
import com.dataextraction.persistence.IDataExtractionDao;

@Service
public class DataExtractionServiceImpl implements IDataExtractionService {

	@Autowired
	IDataExtractionDao dataExtractionDao;

	public IDataExtractionDao getPnaceaDao() {
		return dataExtractionDao;
	}

	public void setPnaceaDao(IDataExtractionDao pnaceaDao) {
		dataExtractionDao = pnaceaDao;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Override
	public List<User> getUsers() {
		return dataExtractionDao.getUsers();

	}

	@PreAuthorize("hasAuthority('ROLE_USER_INSERT')")
	@Override
	public void insertUser(User user) {
		dataExtractionDao.insertUser(user);

	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@Override
	public User getUserDetails(String userName) {

		return dataExtractionDao.getUserDetails(userName);
	}

	@PreAuthorize("hasAuthority('ROLE_USER_EMPLOYEE')")
	@Override
	public List<OlympicData> getOlympicData() {

		return dataExtractionDao.getOlympicData();
	}

}
