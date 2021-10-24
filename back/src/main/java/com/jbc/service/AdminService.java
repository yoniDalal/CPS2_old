package com.jbc.service;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.jbc.repo.AdminRepository;
import com.jbc.repo.CompanyRepository;
import com.jbc.repo.CouponRepository;
import com.jbc.repo.CustomerRepository;
import com.jbc.repo.LoggerRepository;
import com.jbc.service.ifc.AdminServiceIFC;
import com.jbc.util.ActionsType;
import com.jbc.util.ClientType;

import io.jsonwebtoken.lang.Collections;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see ifc#AdminServiceIFC
 * @see factory#InitializingBean
 * @see user#CompanyRepository
 * @see user#CustomerRepository
 * @see user#AdminRepository
 * @see repository#CouponRepository
 * @see repository#LoggerRepository
 */
@Service
public class AdminService implements AdminServiceIFC , InitializingBean{

	/*attributes*/
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private LoggerRepository logRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<Admin> login(String email, String password) {
		return adminRepo.findByEmailIgnoreCaseAndPassword(email, password);
	}

	@Override
	public Company addCompany(Company company, long adminId) throws AlreadyExistsException, IsNullException {
		checkNameEmail(company);
		Admin admin = adminRepo.findById(adminId).get();
		if(compRepo.findById(company.getId()).isPresent())
		{
			throw new AlreadyExistsException("Company", "ID");
		}
		company.setPassword(passwordEncoder.encode(company.getPassword()));
		
		logRepo.save(new Logger(ActionsType.CREATE,
				ClientType.COMPANY,
				company.getId(),
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				company.getName()+", "+company.getEmail(),
				"--"));
		return compRepo.save(company);
	}

	@Override
	public Company updateCompany(Company company,long adminId) throws DoesNotExistByIDException, AlreadyExistsException, IsNullException {

		checkNameEmail(company);
		Company beforUpdate = compRepo.findById(company.getId()).get();
		Admin admin = adminRepo.findById(adminId).get();

		if(!compRepo.findById(company.getId()).isPresent())
		{
			throw new DoesNotExistByIDException("Company", company.getId());
		}

		if (!company.getPassword().equals(beforUpdate.getPassword())) {
			company.setPassword(passwordEncoder.encode(company.getPassword()));
		}
		company.setCoupons(beforUpdate.getCoupons());
		logRepo.save(new Logger(ActionsType.UPDATE,
				ClientType.COMPANY,
				company.getId(),
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				company.getName()+", "+company.getEmail()+", ",
				beforUpdate.getName()+", "+beforUpdate.getEmail()+", "));
		return compRepo.save(company);
	}

	@Override
	public void deleteCompany(long companyId,long adminId) throws DoesNotExistByIDException {
		Admin admin = adminRepo.findById(adminId).get();
		Company beforUpdate = compRepo.findById(companyId).get();
		if (!compRepo.findById(companyId).isPresent()) {

			throw new DoesNotExistByIDException("Company",companyId);
		}

		logRepo.save(new Logger(ActionsType.DELETE,
				ClientType.COMPANY,
				companyId,
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				"--",
				beforUpdate.getName()+", "+beforUpdate.getEmail()+", "));
		compRepo.deleteById(companyId);
	}

	@Override
	public List<Company> getAllCompanies() throws MessageException {
		List<Company> companies = compRepo.findAll();
		if(companies.size()==0)
		{
			throw new MessageException("There are no Companies");
		}
		return companies;
	}

	@Override
	public Company getOneCompany(long companyId) throws DoesNotExistByIDException {
		Optional<Company> company= compRepo.findById(companyId);
		if(!company.isPresent())
		{
			throw new DoesNotExistByIDException("Company",companyId);
		}
		return company.get();
	}

	@Override
	public Customer addCustomer(Customer customer,long adminId) throws AlreadyExistsException, MessageException {
		Admin admin = adminRepo.findById(adminId).get();
		checkEmail(customer);
		if(custRepo.findById(customer.getId()).isPresent())
		{
			throw new AlreadyExistsException("Customer", customer.getFirstName());
		}

		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		
		logRepo.save(new Logger(ActionsType.CREATE,
				ClientType.CUSTOMER,
				customer.getId(),
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				customer.getFirstName()+", "+customer.getLastName()+", "+customer.getEmail(),
				"--"));
		return	custRepo.save(customer);
	
	}

	@Override
	public Customer updateCustomer(Customer customer,long adminId) throws DoesNotExistByIDException, AlreadyExistsException, MessageException {
		Admin admin = adminRepo.findById(adminId).get();
		Customer beforUpdate = custRepo.findById(customer.getId()).get();
		checkEmail(customer);
		if(!custRepo.findById(customer.getId()).isPresent())
		{
			throw new DoesNotExistByIDException("Customer",customer.getId());
		}
		if (!customer.getPassword().equals(beforUpdate.getPassword())) {
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		}
		customer.setCoupons(beforUpdate.getCoupons());
		logRepo.save(new Logger(ActionsType.UPDATE,
				ClientType.CUSTOMER,
				customer.getId(),
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				customer.getFirstName()+", "+customer.getLastName()+", "+customer.getEmail()+", ",
				beforUpdate.getFirstName()+", "+beforUpdate.getLastName()+", "+beforUpdate.getEmail()+", "));
		return custRepo.save(customer);
	}

	@Override
	public void deleteCustomer(long customerId,long adminId) throws DoesNotExistByIDException {
		Admin admin = adminRepo.findById(adminId).get();
		Customer beforUpdate = custRepo.findById(customerId).get();
		if (!custRepo.findById(customerId).isPresent()) {
			throw new DoesNotExistByIDException("Customer",customerId);
		}
		
		for (Coupon customerCoupon : coupRepo.findByCustomerId(customerId)) {
			logRepo.save(new Logger(ActionsType.DELETE,
					ClientType.COUPON,
					customerId,
					ClientType.ADMIN,
					admin.getEmail()+", ID-"+adminId,
					"--",
					customerCoupon.getTitle()+", "+customerCoupon.getDescription()+", "+customerCoupon.getId()));
			
		}
	
		custRepo.deleteById(customerId);
		logRepo.save(new Logger(ActionsType.DELETE,
				ClientType.CUSTOMER,
				customerId,
				ClientType.ADMIN,
				admin.getEmail()+", ID-"+adminId,
				"--",
				beforUpdate.getFirstName()+", "+beforUpdate.getLastName()+", "+beforUpdate.getEmail()+", "));
		
	}

	@Override
	public List<Customer> getAllCustomers() throws MessageException {
		List<Customer> customers = custRepo.findAll();
		if (customers.size()==0) {
			throw new MessageException("There are no Customers");
		}
		return customers;
	}

	@Override
	public Customer getOneCustomer(long customerId) throws DoesNotExistByIDException {
		Optional<Customer> customer = custRepo.findById(customerId);
		if (!customer.isPresent()) {
			throw new DoesNotExistByIDException("Customer",customerId);
		}
		return customer.get();
	}

	@Override
	public List<Coupon> getAllCoupon() throws MessageException {
		List<Coupon> coupons = coupRepo.findAll();
		if (coupons.size()==0) {
			throw new MessageException("There are no Coupons");
		}
		return coupons;
	}

	/**
	 * checks if a {@code Customer} {@code Entity} with the same {@code email} already exists in the system.
	 * 
	 * @param customer
	 * @throws AlreadyExistsException			if a {@code Customer} {@code Entity}
	 *                                         with the same {@code email} already
	 *                                         exists in the system.
	 * @throws MessageException
	 */
	private void checkEmail(Customer customer) throws AlreadyExistsException, MessageException {
		if (customer==null) {
			throw new MessageException("Coupon price does not found");
		}

		if(custRepo.findByEmailIgnoreCaseAndIdNot(customer.getEmail(), customer.getId()).isPresent())
		{
			throw new AlreadyExistsException("Customer", "Email");
		}
	}

	/**
	 * Checks if a Company Entity with the same name or email already exists in the system
	 * 
	 * @param company
	 * @throws AlreadyExistsException		 if a {@code Company} {@code Entity}
	 *                                        with the same {@code name} or
	 *                                        {@code email} already exists in the
	 *                                        system.
	 * @throws IsNullException				 if the {@code Company} value is
	 *                                        {@code null}.
	 */
	private void checkNameEmail(Company company) throws AlreadyExistsException, IsNullException {
		if (company==null) {
			throw new IsNullException("Company");
		}
		if (compRepo.findByNameIgnoreCaseAndIdNot( company.getName(), company.getId()).isPresent()) {
			throw new AlreadyExistsException("Company", "Name");
		}
		if (compRepo.findByEmailIgnoreCaseAndIdNot( company.getEmail(), company.getId()).isPresent()) {
			throw new AlreadyExistsException("Company", "Email");
		}
	}

	@Override
	public Company getCompanyByEmail(String email) throws DoesNotExistByEmailException {
		Optional<Company> company = compRepo.findByEmailIgnoreCase(email);
		if (!company.isPresent()) {
			throw new DoesNotExistByEmailException("Company", email);
		}
		return company.get();
	}

	@Override
	public Customer getCustomerByEmail(String email) throws DoesNotExistByEmailException {
		Optional<Customer> customer = custRepo.findByEmailIgnoreCase(email);
		if (!customer.isPresent()) {
			throw new DoesNotExistByEmailException("Customer", email);
		}
		return customer.get();
	}
	@Override
	public List<Logger> getLogs() throws MessageException{
		List<Logger> logsList= logRepo.findAll();
		java.util.Collections.sort(logsList);
		if (logsList.size()==0) {
			throw new MessageException("There are no logs");
		}
		return logsList;
	}
	
	@Override
	public void clearLogs() {
		logRepo.deleteAll();
	}
	
	

	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (adminRepo.findAll().isEmpty()) {
			adminRepo.save(new Admin( passwordEncoder.encode("admin") ,"admin@admin.com"));
			adminRepo.save(new Admin(passwordEncoder.encode("Eden") , "eden@admin.com" ));
		}
	}
}