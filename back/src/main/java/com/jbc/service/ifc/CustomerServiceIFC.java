package com.jbc.service.ifc;


import java.util.List;

import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.MessageException;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see service#CustomerService
 */

public interface CustomerServiceIFC {

	/**
	 * 
	 * @param email
	 * @param password
	 * @return {@code Entity} of the {@code Customer} with the {@code email} and
	 * {@code password}.
	 */
	public boolean login(String email, String password);

	/**
	 * Add a new {@link com.jbc.model.Coupon} based on the
	 * {@code couponId} to a {@link com.jbc.model.Customer} {@code Entity},
	 * based on the {@code customerId}.
	 * @param couponId
	 * @param customerId
	 * @return the purchased {@code Coupon} {@code Entity}.
	 * @throws DoesNotExistByIDException	  if a {@code Coupon} {@code Entity}
	 *                                        with the {@code couponId} does not
	 *                                        exist
	 * @throws MessageException
	 */
	public Coupon purchaseCoupon(long couponId, long customerId) throws DoesNotExistByIDException, MessageException;

	/**
	 * 
	 * @param customerId
	 * @return the {@code List} of the {@link com.jbc.model.Coupon} {@code Entity}
	 * that belong to the {@link com.jbc.model.Customer} {@code Entity} based on the
	 * {@code customerId}.
	 * 
	 * @throws MessageException
	 */
	public List<Coupon> getCustomerCoupons(long customerId) throws MessageException;

	/**
	 * 
	 * @param category
	 * @param customerId
	 * @return the {@code List} of the {@link com.jbc.model.Coupon} {@code Entity}
	 * that belong to the {@link com.jbc.model.Customer} {@code Entity} based on the
	 * {@code customerId} and the
	 * {@link com.jbc.model.Category}.
	 * 
	 * @throws MessageException
	 */
	public List<Coupon> getCustomerCouponsByCategory(Category category, long customerId) throws MessageException; 

	/**
	 * 
	 * @param maxPrice
	 * @param customerId
	 * @return the {@code List} of the {@link com.beans.Coupon}
	 * {@code Entity} that belong to the {@link com.jbc.model.Customer} {@code Entity} based on
	 * the {@code customerId} and {@code maxPrice}
	 * 
	 * @throws MessageException
	 */
	public List<Coupon> getCustomerCouponsByMax(double maxPrice, long customerId) throws MessageException;

	/**
	 * 
	 * @param customerId
	 * @return  the {@link com.jbc.model.Customer} {@code Entity} based on the
	 * {@code customerId}.
	 */
	public Customer getCustomerDetails(long customerId);
}