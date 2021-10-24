package com.jbc.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jbc.exceptions.DoesNotExistByEmailException;
import com.jbc.exceptions.MessageException;
import com.jbc.message.response.JwtResponse;
import com.jbc.model.User;
import com.jbc.security.jwt.JwtProvider;

import com.jbc.security.service.UserPrinciple;
import com.jbc.util.ClientType;

import javassist.expr.NewArray;

/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see service#AdminService
 * @see service#CompanyService
 * @see service#CustomerService
 */

@Component
public class LoginManager {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtProvider jwtProvider;

	/* attributes */
	@Autowired
	AdminService adminService;
	@Autowired
	CompanyService companyService;
	@Autowired
	CustomerService customerService;

	

	/**
	 * Login as a client based on {@code ClientType} {@code email} and
	 * {@code password}
	 * 
	 * @param email
	 * @param password
	 * @param clientType
	 * @return {@code AdminService} {@code companyService} {@code customerService} 
	 * based on the {@code ClientType}
	 * @throws DoesNotExistByEmailException		if the {@code email} is not exist.
	 * @throws MessageException 
	 * 
	 */
	public ResponseEntity<?> login(String email, String password, ClientType clientType) throws DoesNotExistByEmailException, MessageException{

		Authentication authentication = null;
		try {
			authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (Exception e) {
			throw new MessageException("Login failed, user name or password incorrect");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtProvider.generateJwtToken(authentication);
		UserPrinciple userDetails = (UserPrinciple)authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = null;

		switch (clientType) {
		case ADMIN:
			if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.ADMIN.name()))) {
				authorities=new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.ADMIN.name())));
			}
			break;
		case CUSTOMER:
			if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.CUSTOMER.name()))) {
				authorities=new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.CUSTOMER.name())));
			}
			break;
		case COMPANY:
			if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(ClientType.COMPANY.name()))) {
				authorities=new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority(ClientType.COMPANY.name())));
			}
			break;

		default:
			break;
		}
		
		if (authorities==null) {
			 throw new MessageException("Login failed, account exsist but under diferent user type");
		}
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), authorities));

	}
}