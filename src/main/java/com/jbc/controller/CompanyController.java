package com.jbc.controller;


import java.nio.file.attribute.UserPrincipal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.security.service.UserPrinciple;
import com.jbc.service.ifc.CompanyServiceIFC;

@CrossOrigin(origins="*" ,maxAge=3600)
@RestController
@RequestMapping(value = "/company")
public class CompanyController {

	@Autowired
	CompanyServiceIFC compServ;

	@RequestMapping(value = "/get-company-details", method = RequestMethod.GET)
	public ResponseEntity<?> getCompanyDetails() {
		return new ResponseEntity<Company>(compServ.getCompanyDetails(getCompanyId()), HttpStatus.OK);
	}

	@RequestMapping(value = "/get-company-coupons-by-max-price/{maxPrice}", method = RequestMethod.GET)
	public ResponseEntity<?> getCompanyCouponsByMax(@PathVariable double maxPrice) {
		List<Coupon> coupons = null;
		try {
			coupons = compServ.getCompanyCouponsByMax(maxPrice, getCompanyId());
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-company-coupons-by-category/{category}", method = RequestMethod.GET)
	public ResponseEntity<?> getCompanyCouponsByCategory(@PathVariable Category category) {
		List<Coupon> coupons = null;
		try {
			coupons = compServ.getCompanyCouponsByCategory(category, getCompanyId());
		} catch (MessageException e) { // the wrapper is here to get exceptions in JSON format
			// string doesnt get sent as JSON
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-company-coupons", method = RequestMethod.GET)
	public ResponseEntity<?> getCompanyCoupons() {
		List<Coupon> coupons = null;
		try {
			coupons = compServ.getCompanyCoupons(getCompanyId());
		} catch (MessageException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<List<Coupon>>(coupons, HttpStatus.OK);
	}
	// -------------------------------------------------------------------------------------------------------------------
	/* ????????? */
			@RequestMapping(value = "/delete-company-coupon/{couponId}", method = RequestMethod.DELETE)
			public void deleteCompanyCoupon(@PathVariable long couponId){
					try {
						compServ.deleteCoupon(couponId, getCompanyId());
					} catch (DoesNotExistByIDException e) {
						e.printStackTrace();
					}
			}
	// -------------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = "/update-company-coupon",  produces = { "application/json;charset=utf-8" },consumes = { "application/json;charset=utf-8" }, method = RequestMethod.PUT)	
	public ResponseEntity<?> updateCompanyCoupon(@RequestBody Coupon coupon){
		try {
			compServ.updateCoupon(coupon, getCompanyId());
		} catch (DoesNotExistByIDException|MessageException|IsNullException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}

	@RequestMapping(value = "/add-company-coupon",  produces = { "application/json;charset=utf-8" },consumes = { "application/json;charset=utf-8" }, method = RequestMethod.POST)
	public ResponseEntity<?> addCompanyCoupon(@RequestBody Coupon coupon){
		
		try {
			compServ.addCoupon(coupon, getCompanyId());
		} catch (AlreadyExistsException|MessageException|IsNullException e) {
			return new ResponseEntity<StringWrapper>(new StringWrapper(e.toString()), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
	}
	
	
	public long getCompanyId() {
		Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long id = 1;
		if(principle instanceof UserPrinciple) {
			id = ((UserPrinciple)principle).getId();
		}
		return id;
	}


}






