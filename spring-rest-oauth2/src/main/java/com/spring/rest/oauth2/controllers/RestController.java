package com.spring.rest.oauth2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.rest.oauth2.models.User;
import com.spring.rest.oauth2.services.DataService;

@Controller
@RequestMapping("/api/users")
public class RestController {

	@Autowired
	private DataService dataService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<User> list() {
		return dataService.getUserList();

	}
}
