package com.jbc.test;

import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByEmailException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.service.AdminService;
import com.jbc.service.CompanyService;
import com.jbc.service.CustomerService;

/**
 * {@code Component} which contains a {@link #testAll()} method for the testing
 * of the services, to ensure that their methods correctly.
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see service#CustomerService
 * @see service#CompanyService
 * @see service#AdminService
 */
@Component
public class Test {

	/* attributes */
	@Autowired
	private CustomerService custServ;
	@Autowired
	private CompanyService compServ;
	@Autowired
	private AdminService adminServ;

	/**
	 * Calls all the methods in the {@link com.jbc.service.AdminService} {@link com.jbc.service.CompanyService}
	 * {@link com.jbc.service.CustomerService}
	 * 
	 * @throws AlreadyExistsException
	 * @throws MessageException
	 * @throws DoesNotExistByIDException
	 * @throws IsNullException
	 * @throws DoesNotExistByEmailException
	 */
	public void testAll() throws AlreadyExistsException, MessageException, DoesNotExistByIDException, IsNullException, DoesNotExistByEmailException {

//		try {

			DBCreator.createDatabase(adminServ, compServ, custServ);
//
//			/* Admin methods */
//
//			adminServ.addCompany(new Company("issta", "yonidalal@issta.col.il", "12345"), 1);
//			Coupon coupon= new Coupon(Category.AUTOMOBILE, "edennnn", "aaaaa", Date.valueOf("2021-02-02"), Date.valueOf("2022-03-03"), 555, 1000.0, "hi");
//			compServ.addCoupon(coupon, 1);
//
//			Customer cust = new Customer("miri", "bachner", "eden@gmail.com", "11111");
//			adminServ.addCustomer(cust, 1);
//
//			adminServ.deleteCompany(2, 2);
//			adminServ.deleteCustomer(22, 1);
//
//			Company company= new Company("Toyota2020", "kjuhdsfgkghjfadrghjk@gmail.com", "12345");
//			company.setId(5);
//			adminServ.updateCompany(company, 2);
//
//			/* Before DB to check ex */
//			//adminServ.getAllCompanies().stream().forEach(System.out :: println); option b
//			adminServ.getAllCompanies().stream().forEach(stream->System.out.println(stream));
//
//			Customer cust2= new Customer("miri", "bachner", "mirBach@gmail.com", "11111");
//			cust2.setId(10);
//			adminServ.updateCustomer(cust2, 2);
//
//			adminServ.getAllCustomers().stream().forEach(stream->System.out.println(stream));
//
//			System.out.println(adminServ.getOneCustomer(10));
//
//			adminServ.getAllCoupon().stream().forEach(stream->System.out.println(stream));
//
//			System.out.println(adminServ.getOneCompany(6));
//
//			System.out.println(adminServ.getCompanyByEmail("tasa@gmail.com"));
//
//			System.out.println(adminServ.getCustomerByEmail("bevi@gmail.com"));
//
//
//			/* Company methods */
//			compServ.addCoupon(new Coupon(Category.AUTOMOBILE, "edennnn2", "aaaaa", Date.valueOf("2021-02-02"), Date.valueOf("2022-03-03"), 555, 1000.0, "hi"), 1);
//
//			/* check null ex */
//			//	compServ.addCoupon(null, 5);
//
//			Coupon coupon2= new Coupon(Category.AUTOMOBILE, "edennnn", "aaaaa", Date.valueOf("2021-02-02"), Date.valueOf("2022-03-03"), 555, 1000.0, "hi");
//			coupon2.setId(4);
//
//			/* check null ex */
//			//compServ.updateCoupon(null, 10);
//
//			Coupon coupon3= new Coupon(Category.AUTOMOBILE, "edennnn4", "aaaaa", Date.valueOf("2021-02-02"), Date.valueOf("2022-03-03"), 555, 1000.0, "hi");
//			compServ.addCoupon(coupon3, 1);
			
//			compServ.deleteCoupon(17, 9);		
//
//			compServ.getCompanyCoupons(9).stream().forEach(stream->System.out.println(stream));	
//
//			/*
//			for (Coupon coupon4 : compServ.getCompanyCoupons(9)) {   
//				System.out.println(coupon4);
//			}
//			 */
//
//			compServ.getCompanyCouponsByCategory(Category.ELECTRICITY, 4).stream().forEach(stream->System.out.println(stream));
//			/*
//			for (Coupon coupon5 : compServ.getCompanyCouponsByCategory(Category.ELECTRICITY, 4)) {   
//				System.out.println(coupon5);
//			}
//			 */
//
//
//			compServ.getCompanyCouponsByMax(1000.0, 5).stream().forEach(stream->System.out.println(stream));
//			/*
//			for (Coupon coupon6 : compServ.getCompanyCouponsByMax(1000.0, 5)) {  
//				System.out.println(coupon6);
//
//			}
//			 */
//			compServ.getCompanyCouponsByMax(1000.0, 5).stream().forEach(stream->System.out.println(stream));
//
//			System.out.println(compServ.getCompanyDetails(8));
//
//			/* Customer methods */
//			custServ.purchaseCoupon(7, 7);;
//
//			custServ.getCustomerCoupons(11).stream().forEach(stream->System.out.println(stream));
//			/*
//			for (Coupon coup : custServ.getCustomerCoupons(11)) {  
//				System.out.println(coup);
//			}
//			 */
//
//			custServ.getCustomerCouponsByMax(500, 1).stream().forEach(stream->System.out.println(stream));
//			/*
//			for (Coupon coup : custServ.getCustomerCouponsByMax(500, 1)) {  // 
//				System.out.println(coup);
//			}
//			 */
//
//			custServ.getCustomerCouponsByCategory(Category.AUTOMOBILE, 2).stream().forEach(stream->System.out.println(stream));
//			/*
//			for (Coupon coup : custServ.getCustomerCouponsByCategory(Category.AUTOMOBILE, 2)) {  
//				System.out.println(coup);
//			}
//			 */
//			System.out.println(compServ.getCompanyDetails(1));
//
//			Customer cust3= new Customer("Edennn", "BaChNeR", "Beden@gmail.com", "01121965");
//			cust3.setId(11);
//			adminServ.updateCustomer(cust3, 2);
//
//			System.out.println("Done!");
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			/*
//			 * System.out.println("\nTesting completed successfully."); } catch
//			 * (MessageException e) { System.out.println("\nTesting was unsuccessful.");
//			 * System.err.println(e); } finally { System.out.println("Test ended.\n");
//			 */
//		}
	}
}