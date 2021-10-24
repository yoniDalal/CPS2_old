package com.jbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByEmailException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.logger.Logger;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.security.service.UserPrinciple;
import com.jbc.service.ifc.AdminServiceIFC;

@CrossOrigin(origins="*" ,maxAge=3600)
@RestController
@RequestMapping(value = "/admin")
public class AdminController {


	@Autowired
	AdminServiceIFC adminServ;

	@RequestMapping(value = "/get-customer-by-email/{email}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
		Customer customer = null;
		try {
			customer=adminServ.getCustomerByEmail(email);
		} catch (DoesNotExistByEmailException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-company-by-email/{email}", method = RequestMethod.GET)
	public ResponseEntity<?> getCompanyByEmail(@PathVariable String email) {
		Company company = null;
		try {
			company=adminServ.getCompanyByEmail(email);
		} catch (DoesNotExistByEmailException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-all-coupons", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCoupons() {
		List<Coupon> coupons = null;
		try {
			coupons = adminServ.getAllCoupon();
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-one-customer-by-customerId/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<?> getOneCustomer(@PathVariable Long customerId) {
		Customer customer = null;
		try {
			customer = adminServ.getOneCustomer(customerId);
		} catch (DoesNotExistByIDException e) { 
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}

	@RequestMapping(value = "/get-all-customers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCustomers() {
		List<Customer> customers = null;
		try {
			customers = adminServ.getAllCustomers();
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete-customer/{customerId}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable long customerId){
		try {
			adminServ.deleteCustomer(customerId, getAdmin());
		} catch (DoesNotExistByIDException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/update-customer", produces = { "application/json;charset=utf-8" },consumes = { "application/json;charset=utf-8" }, method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
		try {
			adminServ.updateCustomer(customer, getAdmin());
		} catch (DoesNotExistByIDException|MessageException| AlreadyExistsException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add-customer", produces = { "application/json;charset=utf-8" },consumes = { "application/json;charset=utf-8" }, method = RequestMethod.POST)
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
		try {
			adminServ.addCustomer(customer, getAdmin());
		} catch (AlreadyExistsException|MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-one-company-by-companyId/{companyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getOneCompany(@PathVariable Long companyId) {
		Company company = null;
		try {
			company = adminServ.getOneCompany(companyId);
		} catch (DoesNotExistByIDException e) { 
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Company>(company,HttpStatus.OK);
	}
		
	@RequestMapping(value = "/get-all-companies", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCompanies() {
		List<Company> companies = null;
		try {
			companies = adminServ.getAllCompanies();
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
	}
		
	@RequestMapping(value = "/delete-company/{companyId}", method = RequestMethod.DELETE)
	public void deleteCompany(@PathVariable long companyId){
		try {
			adminServ.deleteCompany(companyId, getAdmin());
		} catch (DoesNotExistByIDException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/update-company", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCompany(@RequestBody Company company){
		Company comp = null;
		try {
			comp=adminServ.updateCompany(company, getAdmin());
		} catch (DoesNotExistByIDException|IsNullException| AlreadyExistsException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Company>(comp, HttpStatus.OK);
	}

	@RequestMapping(value = "/add-company", produces = { "application/json;charset=utf-8" },consumes = { "application/json;charset=utf-8" }, method = RequestMethod.POST)
	public ResponseEntity<?> addCompany(@RequestBody Company company){
		try {
			adminServ.addCompany(company, getAdmin());
		} catch (AlreadyExistsException|IsNullException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Company>(company, HttpStatus.OK);
	}
	@RequestMapping(value = "/get-logs", produces = { "application/json;charset=utf-8" }, method = RequestMethod.GET)
	public ResponseEntity<?> getLogs(){
		try {
			return new ResponseEntity<List<Logger>>( adminServ.getLogs(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		
	}
	@RequestMapping(value = "/clear-logs", produces = { "application/json;charset=utf-8" }, method = RequestMethod.DELETE)
	public ResponseEntity<?> clearLogs(){
		adminServ.clearLogs();
		return new ResponseEntity<StringWrapper>(new StringWrapper("Success"), HttpStatus.OK);
	}

	
	public long getAdmin() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = 1;
		if(principle instanceof UserPrinciple) {
			id = ((UserPrinciple)principle).getId();
		}
		return id;
	}
}

