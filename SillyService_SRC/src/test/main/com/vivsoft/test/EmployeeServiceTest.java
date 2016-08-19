package com.vivsoft.test;

import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class EmployeeServiceTest {

	@Test
	public void testGetsccess_token() {
	
		String url = "http://localhost:8080/SillyService_SRC/oauth/token?grant_type=password&client_id=my-trusted-client&username=admin&password=password";
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> entity = new HttpEntity<String>("", headers);

		ResponseEntity<String> loginResponse = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println("RESPONSE : "+loginResponse.getBody());
		String access_token = null;
		try {
			JSONObject json = (JSONObject)new JSONParser().parse(loginResponse.getBody());
			access_token = json.get("access_token").toString();
			
			System.out.println("------------------------------------------");
			System.out.println("access_token     : " + json.get("access_token"));
			System.out.println("refresh_token    : " + json.get("refresh_token"));
			System.out.println("token_type       : " + json.get("token_type"));
			System.out.println("scope            : " + json.get("scope"));
			System.out.println("expires_in       : " + json.get("expires_in"));
			System.out.println("------------------------------------------");
		} catch (ParseException e) {
			System.out.println("Parse Exception occured : "+e.getErrorType());
		}
	
		
		
		RestTemplate employeeTemplate = new RestTemplate();
		String employeeUrl = "http://localhost:8080/SillyService_SRC/employee/list?access_token="+access_token;
		ResponseEntity<String> employeeResponse = employeeTemplate.exchange(employeeUrl, HttpMethod.GET, entity, String.class);
		System.out.println("--------------------------------------------");
		System.out.println("Employee RESPONSE : "+employeeResponse.getBody());
	}

}
