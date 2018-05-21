package com.dataextraction.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dataextraction.model.GroupResponse;
import com.dataextraction.model.OlympicData;
import com.dataextraction.model.User;
import com.dataextraction.security.oauth2.service.IDataExtractionService;

@RestController
@RequestMapping("secured/")
public class RestServiceController {

	@Autowired
	IDataExtractionService dataExtractionService;

	// @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
	// public GroupResponse<User> login(@RequestBody User request) {
	// GroupResponse<User> res = new GroupResponse<>();
	//
	// User user = dataExtractionService.login(request.getUserName(),
	// request.getPassword());
	//
	// List<User> list = new ArrayList<>();
	// list.add(user);
	// res.setItems(list);
	// res.setIsSuccess(true);
	// return res;
	// }

	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public GroupResponse<User> getUsers() {
		GroupResponse<User> res = new GroupResponse<>();
		res.setIsSuccess(true);

		List<User> list = new ArrayList<>();
		list = dataExtractionService.getUsers();
		res.setItems(list);
		return res;
	}

	@RequestMapping(value = "/api/addUser", method = RequestMethod.POST)
	public GroupResponse<User> addUser(@RequestBody User request) {
		GroupResponse<User> res = new GroupResponse<>();
		res.setIsSuccess(true);

		dataExtractionService.insertUser(request);

		return res;
	}

	@RequestMapping(value = "/api/getUserDetails", method = RequestMethod.GET)
	public GroupResponse<User> getUserDetails(@RequestParam String userName) {
		GroupResponse<User> res = new GroupResponse<>();
		res.setIsSuccess(true);

		User user = dataExtractionService.getUserDetails(userName);
		List<User> items = new ArrayList<>();
		items.add(user);
		res.setItems(items);
		return res;
	}

	@RequestMapping(value = "/api/getOlympicData", method = RequestMethod.GET)
	public GroupResponse<OlympicData> getOlympicData() {
		GroupResponse<OlympicData> res = new GroupResponse<>();
		res.setIsSuccess(true);

		List<OlympicData> list = new ArrayList<>();
		list = dataExtractionService.getOlympicData();
		res.setItems(list);
		return res;
	}

}
