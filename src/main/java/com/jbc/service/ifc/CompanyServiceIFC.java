package com.jbc.service.ifc;

import java.util.List;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see service#CompanyService
 */

public interface CompanyServiceIFC {

	/**
	 * 
	 * @param email
	 * @param password
	 * @return {@code Entity} of the {@code Company} with the {@code email} and
	 * {@code password}.
	 */
	public boolean login(String email, String password);

	/**
	 * Add a new {@link com.jbc.model.Coupon} 
	 * that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId}.
	 * @param coupon
	 * @param id
	 * @return {@code Coupon} {@code Entity}.
	 * 
	 * @throws AlreadyExistsException		 if a {@code Coupon} {@code Entity} with
	 *                                       the same {@code id} already exists
	 * @throws MessageException
	 * @throws IsNullException				 if the {@code Coupon} value is
	 *                                       {@code null}.
	 */
	public Coupon addCoupon(Coupon coupon, long id) throws AlreadyExistsException, MessageException, IsNullException;

	/**
	 * Update a {@link com.jbc.model.Coupon} 
	 * that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId}.
	 * @param coupon
	 * @param id
	 * @return updated {@code Coupon} {@code Entity}
	 * 
	 * @throws DoesNotExistByIDException	if a {@code Coupon} {@code Entity} with
	 *                                       the {@code couponId} does not exist
	 * @throws MessageException
	 * @throws IsNullException				if the {@code Coupon} value is
	 *                                       {@code null}.
	 */
	public Coupon updateCoupon(Coupon coupon, long id) throws DoesNotExistByIDException, MessageException, IsNullException;

	/**
	 * Deletes a {@link com.jbc.model.Coupon} that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId}.
	 * @param couponID
	 * @param id
	 * 
	 * @throws DoesNotExistByIDException	if a {@code Coupon} {@code Entity} with the
	 *                              	   {@code couponId} does not exist
	 */
	public void deleteCoupon(long couponID, long id) throws DoesNotExistByIDException;

	/**
	 * 
	 * @param id
	 * @return the {@code List} of the {@link com.beans.Coupon}
	 * {@code Entity} that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId}.
	 *         
	 * @throws MessageException
	 */
	public List<Coupon> getCompanyCoupons(long id) throws MessageException;

	/**
	 * 
	 * @param category
	 * @param id
	 * @return the {@code List} of the {@link com.beans.Coupon}
	 * {@code Entity} that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId} and {@link com.jbc.model.Category}
	 * 
	 * @throws MessageException
	 */
	public List<Coupon> getCompanyCouponsByCategory(Category category, long id) throws MessageException ;

	/**
	 * 
	 * @param maxPrice
	 * @param id
	 * @return the {@code List} of the {@link com.beans.Coupon}
	 * {@code Entity} that belong to the {@link com.jbc.model.Company} {@code Entity} based on
	 * the {@code companyId} and {@code maxPrice}
	 * 
	 * @throws MessageException
	 */
	public List<Coupon> getCompanyCouponsByMax(double maxPrice, long id) throws MessageException;

	/**
	 * 
	 * @param id
	 * @return the {@link com.jbc.model.Company} {@code Entity} based on the
	 *         {@code companyId}.
	 */
	public Company getCompanyDetails(long id);
}