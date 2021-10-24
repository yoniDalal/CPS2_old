package com.jbc.service.ifc;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByEmailException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.logger.Logger;
import com.jbc.model.Admin;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;

/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see service#AdminService
 */
public interface AdminServiceIFC {

	/**
	 * 
	 * @param email
	 * @param password
	 * @return {@code Entity} of the {@code Admin} with the {@code email} and
	 * {@code password}.
	 */
	public Optional<Admin> login(String email, String password);

	/**
	 * Add a new {@link com.jbc.model.Company}
	 * @param company
	 * @param adminId
	 * @return {@code Company} {@code Entity}
	 * 
	 * @throws AlreadyExistsException		 if a {@code Company} {@code Entity}
	 *                                        with the same {@code id} already
	 *                                        exists in the system.
	 * @throws IsNullException				 if the {@code Company} value is
	 *                                        {@code null}.
	 */
	public Company addCompany(Company company, long adminId) throws AlreadyExistsException, IsNullException;

	/**
	 * Update a {@link com.jbc.model.Company} that exist.
	 * @param company
	 * @param adminId
	 * @return updated {@code Company} {@code Entity}
	 * 
	 * @throws DoesNotExistByIDException	if a {@code Company} {@code Entity}
	 *                                        with the {@code id} does not exist in
	 *                                        the system.
	 * @throws AlreadyExistsException		if a {@code Company} {@code Entity}
	 *                                        with the same {@code id} already
	 *                                        exists in the system.
	 * @throws IsNullException				if the {@code Company} value is
	 *                                        {@code null}.
	 */
	public Company updateCompany(Company company,long adminId) throws DoesNotExistByIDException, AlreadyExistsException, IsNullException;

	/**
	 * Deletes a {@link com.jbc.model.Company} based on the
	 * {@code companyId}.
	 * @param companyID
	 * @param adminId
	 * 
	 * @throws DoesNotExistByIDException		if a {@code Company} {@code Entity}
	 *                                        with the {@code id} does not exist in
	 *                                        the system.
	 */
	public void deleteCompany(long companyID,long adminId) throws DoesNotExistByIDException; 

	/**
	 * 
	 * @return  the {@code List} of the {@link com.jbc.model.Company}
	 * that exist
	 * @throws MessageException
	 */
	public List<Company> getAllCompanies() throws MessageException;

	/**
	 * 
	 * @param companyID
	 * @return the {@code Company} {@code Entity} with the {@code companyId}
	 * 
	 * @throws DoesNotExistByIDException   	if a {@code Company} {@code Entity}
	 *                                        with the {@code id} does not exist in
	 *                                        the system.
	 */
	public Company getOneCompany(long companyID) throws DoesNotExistByIDException;

	/**
	 * Add a new {@link com.jbc.model.Customer}
	 * @param customer
	 * @param adminId
	 * @return {@code Customer} {@code Entity}
	 * 
	 * @throws AlreadyExistsException		if a {@code Customer} {@code Entity}
	 *                                         with the same {@code id}
	 *                                         already exists.
	 * @throws MessageException
	 */
	public Customer addCustomer(Customer customer,long adminId) throws AlreadyExistsException, MessageException;

	/**
	 * Update a {@link com.jbc.model.Customer} that exist.
	 * @param customer
	 * @param adminId
	 * @return updated {@code Customer} {@code Entity}
	 * 
	 * @throws DoesNotExistByIDException		if a {@code Customer} {@code Entity}
	 *                                         with the {@code id} does not exist
	 * @throws AlreadyExistsException			if a {@code Customer} {@code Entity}
	 *                                         with the same {@code id}
	 *                                         already exists.
	 * @throws MessageException
	 */
	public Customer updateCustomer(Customer customer,long adminId) throws DoesNotExistByIDException, AlreadyExistsException, MessageException;

	/**
	 * Deletes a {@link com.jbc.model.Customer} based on the
	 * {@code companyId}.
	 * @param customerID
	 * @param adminId
	 * @return 
	 * 
	 * @throws DoesNotExistByIDException		if a {@code Customer} {@code Entity}
	 *                                         with the {@code customerID} does not exist
	 */
	public void deleteCustomer(long customerID,long adminId) throws DoesNotExistByIDException;

	/**
	 * 
	 * @return the {@code List} of the {@link com.jbc.model.Customer}
	 * that exist
	 * @throws MessageException
	 */
	public List<Customer> getAllCustomers() throws MessageException;

	/**
	 * 
	 * @param customerID
	 * @return  {@code Customer} {@code Entity} with the {@code customerId}.
	 * 
	 * @throws DoesNotExistByIDException		if a {@code Customer} {@code Entity}
	 *                                         with the {@code customerID} does not exist
	 */
	public Customer getOneCustomer(long customerID) throws DoesNotExistByIDException;

	/**
	 * 
	 * @return the {@code List} of the {@link com.jbc.model.Coupon}
	 * that exist
	 * @throws MessageException
	 */
	public List<Coupon> getAllCoupon() throws MessageException;

	/**
	 * 
	 * @param email
	 * @return the {@code Company} {@code Entity} with the {@code email}.
	 * @throws DoesNotExistByEmailException		if a {@code Company} {@code Entity} with the
	 *                                         {@code email} does not exist
	 */
	public Company getCompanyByEmail(String email) throws DoesNotExistByEmailException;

	/**
	 * 
	 * @param email
	 * @return the {@code Customer} {@code Entity} with the {@code email}.
	 * 
	 * @throws DoesNotExistByEmailException		if a {@code Customer} {@code Entity} with
	 *                                 			  the {@code email} does not exist
	 */
	public Customer getCustomerByEmail(String email) throws DoesNotExistByEmailException;
	
	
	public List<Logger> getLogs() throws MessageException;

	public void clearLogs();
	
	
}