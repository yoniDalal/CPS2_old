package com.jbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.service.LoginManager;
import com.jbc.util.ClientType;

@CrossOrigin(origins="*" ,maxAge=3600)
@RestController
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	LoginManager loginManager;
	
	@RequestMapping(value = "/login/{email}/{password}/{clientType}" , method = RequestMethod.POST)
	public ResponseEntity<?> login(@PathVariable String email,@PathVariable String password,@PathVariable ClientType clientType){
		try {
			return loginManager.login(email, password, clientType);
		} catch (Exception e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		
	}

}

