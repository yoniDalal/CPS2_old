package com.jbc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.MessageException;
import com.jbc.logger.Logger;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.repo.CouponRepository;
import com.jbc.repo.CustomerRepository;
import com.jbc.repo.LoggerRepository;
import com.jbc.service.ifc.CustomerServiceIFC;
import com.jbc.util.ActionsType;
import com.jbc.util.ClientType;
/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see ifc#CustomerServiceIFC
 * @see user#CustomerRepository
 * @see repository#CouponRepository
 * @see repository#LoggerRepository
 */
@Service
public class CustomerService implements CustomerServiceIFC{

	/* attributes */
	@Autowired
	CustomerRepository custrepo;
	@Autowired
	CouponRepository coupRepo;
	@Autowired
	private LoggerRepository logRepo;


	@Override
	public boolean login(String email, String password) {
		return custrepo.findByEmailIgnoreCaseAndPassword(email, password).isPresent();
	}

	@Override
	public Coupon purchaseCoupon(long couponId, long customerId) throws DoesNotExistByIDException, MessageException {
		Optional<Coupon> optionalCoupon = coupRepo.findById(couponId);
		if (!optionalCoupon.isPresent()) {
			throw new DoesNotExistByIDException("Coupon", couponId);
		}
		Coupon coupon = optionalCoupon.get();

		if (coupon.getAmount()<=0) {
			throw new MessageException("Coupon amount is 0");
		}
		if (coupon.getEndDate().getTime()<System.currentTimeMillis()) {
			throw new MessageException("Coupon expired");	
		}
		if (coupRepo.findByCustomerIdAndId(customerId,couponId).isPresent()) {
			throw new MessageException("Coupon already purchase");	
		}
		coupon.setAmount(coupon.getAmount()-1);
		Customer customer = custrepo.findById(customerId).get();
		customer.getCoupons().add(coupon);
		custrepo.save(customer);
		logRepo.save(new Logger(ActionsType.PURCHASE,
				ClientType.COUPON,
				couponId ,
				ClientType.CUSTOMER,
				customer.getFirstName()+" "+customer.getLastName()+" ID- "+customer.getId(),
				coupon.getTitle()+", "+coupon.getDescription()+", "+coupon.getCategory(),
				"--"));
		return coupRepo.save(coupon);
	}

	@Override
	public List<Coupon> getCustomerCoupons(long customerId) throws MessageException {
		List<Coupon> coupons = coupRepo.findByCustomerId(customerId);
		if (coupons.size()==0) {
			throw new MessageException("Customer Coupon did not found");

		}
		return coupons;
	}

	@Override
	public List<Coupon> getCustomerCouponsByCategory(Category category, long customerId) throws MessageException {
		List<Coupon> coupons =coupRepo.findByCategoryAndCustomerId(category, customerId);
		if (coupons.size()==0) {
			throw new MessageException("There are no Coupons with this Category");

		}
		return coupons;
	}

	@Override
	public List<Coupon> getCustomerCouponsByMax(double maxPrice, long customerId) throws MessageException {
		List<Coupon> coupons = coupRepo.findByCustomerIdAndMaxPrice(maxPrice, customerId);
		if (coupons.size()==0) {
			throw new MessageException("There are no coupons in this price range");
		}

		return coupons;
	}

	@Override
	public Customer getCustomerDetails(long id) {
		return custrepo.findById(id).get();
	}
}