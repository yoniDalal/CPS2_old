package com.jbc.repo;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jbc.model.Category;
import com.jbc.model.Coupon;

/**
 * {@link com.jbc.model.Coupon} {@code JpaRepository} use for the manage of the coupons in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#Coupon
 */
public interface CouponRepository extends JpaRepository<Coupon, Long>{

	/* Company methods */
	public Optional<Coupon> findByTitleIgnoreCaseAndIdNotAndCompanyId(String title,long couponId ,long companyId);

	public Optional<Coupon> findByIdAndCompanyId(long couponId, long companyId);

	public List<Coupon> findByCompanyId(long companyId);

	public List<Coupon> findByCategoryAndCompanyId (Category category , long companyId);

	public List<Coupon>findByCompanyIdAndPriceLessThanEqual (long companyId, double maxPrice);

	/* customer methods */
	@Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon WHERE customer.id=?2 AND coupon.category = ?1")
	public List<Coupon> findByCategoryAndCustomerId(Category category , long customerId);

	@Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon WHERE customer.id=?1")
	public List<Coupon> findByCustomerId (long customerId);

	@Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon WHERE customer.id=?1 AND coupon.id=?2")
	public Optional<Coupon> findByCustomerIdAndId(long customerId, long couponId);

	@Query("SELECT coupon FROM Customer customer JOIN customer.coupons coupon WHERE customer.id=?2 AND coupon.price <= ?1")
	public List<Coupon> findByCustomerIdAndMaxPrice (double maxPrice, long customerId);
}