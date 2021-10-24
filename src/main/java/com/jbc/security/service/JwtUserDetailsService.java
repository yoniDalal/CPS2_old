package com.jbc.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jbc.model.Admin;
import com.jbc.model.Company;
import com.jbc.model.Customer;
import com.jbc.repo.AdminRepository;
import com.jbc.repo.CompanyRepository;
import com.jbc.repo.CustomerRepository;




@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AdminRepository adminrepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Company> user = companyRepository.findByEmailIgnoreCase(username);
		if (user.isPresent()) {
			return UserPrinciple.build(user.get());
		}
		Optional<Customer> user2 = customerRepository.findByEmailIgnoreCase(username);
		if (user2.isPresent()) {
			return UserPrinciple.build(user2.get());
		}
		Optional<Admin> user3 = adminrepository.findByEmailIgnoreCase(username);
		if (user3.isPresent()) {
			return UserPrinciple.build(user3.get());
		}
		throw new UsernameNotFoundException("User name not found"+username);
	
	}
}
