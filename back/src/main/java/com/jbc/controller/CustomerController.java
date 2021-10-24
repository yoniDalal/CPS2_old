package com.jbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.MessageException;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.security.service.UserPrinciple;
import com.jbc.service.ifc.CustomerServiceIFC;

@CrossOrigin(origins="*" ,maxAge=3600)
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	@Autowired
	CustomerServiceIFC custServ;

	
	@RequestMapping(value = "/get-customer-details", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCustomerDetails() {
		return new ResponseEntity<Customer>(custServ.getCustomerDetails(getCustomerId()), HttpStatus.OK);
	}

	@RequestMapping(value = "/purchase-coupon/{couponId}", method = RequestMethod.PUT)
	public ResponseEntity<?> purchaseCoupon(@PathVariable long couponId) {
		Coupon coupon = null;
		try {
			coupon = custServ.purchaseCoupon(couponId, getCustomerId());
		} catch (DoesNotExistByIDException | MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-customer-coupons", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerCoupons() {
		List<Coupon> coupons = null;
		try {
			coupons = custServ.getCustomerCoupons(getCustomerId());
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-customer-coupons-by-category/{category}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerCouponsByCategory(@PathVariable Category category) {
		List<Coupon> coupons = null;
		try {
			coupons = custServ.getCustomerCouponsByCategory(category, getCustomerId());
		} catch (MessageException e) { // the wrapper is here to get exceptions in JSON format
										// string doesnt get sent as JSON
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-customer-coupons-by-max-price/{maxPrice}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerCouponsByMax(@PathVariable double maxPrice) {
		List<Coupon> coupons = null;
		try {
			coupons = custServ.getCustomerCouponsByMax(maxPrice, getCustomerId());
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	
	public long getCustomerId() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = 0;
		if(principle instanceof UserPrinciple) {
			id = ((UserPrinciple)principle).getId();
	}

		return id;
	}

}