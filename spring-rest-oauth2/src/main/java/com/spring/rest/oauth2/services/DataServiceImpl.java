package com.spring.rest.oauth2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.rest.oauth2.models.User;

@Service
public class DataServiceImpl implements DataService {

	@Override
	public List<User> getUserList() {
		// preparing user list with few hard coded values
		List<User> userList = new ArrayList<User>();
		
		userList.add(new User(1, "test1", "test1@test.com", "9898989898"));
		userList.add(new User(2, "test2", "test2@test.com", "9767989898"));
		userList.add(new User(3, "test3", "test3@test.com", "9898459898"));
		userList.add(new User(4, "john", "johndoe@test.com", "1234567890"));
		
		return userList;
	}
}
