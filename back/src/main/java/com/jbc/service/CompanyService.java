package com.jbc.service;

import java.util.List;

import org.hibernate.annotations.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exceptions.AlreadyExistsException;
import com.jbc.exceptions.DoesNotExistByIDException;
import com.jbc.exceptions.IsNullException;
import com.jbc.exceptions.MessageException;
import com.jbc.logger.Logger;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.repo.CompanyRepository;
import com.jbc.repo.CouponRepository;
import com.jbc.repo.CustomerRepository;
import com.jbc.repo.LoggerRepository;
import com.jbc.service.ifc.CompanyServiceIFC;
import com.jbc.util.ActionsType;
import com.jbc.util.ClientType;

/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see ifc#CompanyServiceIFC
 * @see user#CompanyRepository
 * @see user#CustomerRepository
 * @see repository#CouponRepository
 * @see repository#LoggerRepository
 */
@Service
public class CompanyService implements CompanyServiceIFC{

	/*attributes*/
	@Autowired
	private CompanyRepository compRepo;
	@Autowired
	private CouponRepository coupRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private LoggerRepository logRepo;


	@Override
	public boolean login(String email, String password) {
		return compRepo.findByEmailIgnoreCaseAndPassword(email, password).isPresent();
	}

	@Override
	public Coupon addCoupon(Coupon coupon, long companyId) throws AlreadyExistsException, MessageException, IsNullException {
		checkCoupon(coupon, companyId);
		Company company = compRepo.findById(companyId).get();

		if (coupRepo.existsById(coupon.getId())) {
			throw new AlreadyExistsException("Coupon", "ID");
		}
		coupon.setCompany(compRepo.findById(companyId).get());
		logRepo.save(new Logger(ActionsType.CREATE,
				ClientType.COUPON,
				coupon.getId(),
				ClientType.COMPANY,
				company.getName()+", company ID-"+ company.getId(),
				coupon.getTitle()+", "+coupon.getDescription()+", "+coupon.getCategory(),
				"--"));
		return coupRepo.save(coupon);
	}

	@Override
	public Coupon updateCoupon(Coupon coupon, long companyId) throws DoesNotExistByIDException, MessageException, IsNullException {
		checkCoupon(coupon, companyId);
		Company company = compRepo.findById(companyId).get();
		Coupon beforUpdate=coupRepo.findById(coupon.getId()).get();

		if (!coupRepo.findByIdAndCompanyId(coupon.getId(), companyId).isPresent()) {
			throw new DoesNotExistByIDException("Coupon", coupon.getId());
		}
		coupon.setCompany(compRepo.findById(companyId).get());
		logRepo.save(new Logger(ActionsType.UPDATE,
				ClientType.COUPON,
				coupon.getId(),
				ClientType.COMPANY,
				company.getName()+", company ID-"+ company.getId(),
				coupon.getTitle()+", "+coupon.getDescription()+", "+coupon.getCategory(),
				beforUpdate.getTitle()+", "+beforUpdate.getDescription()+", "+beforUpdate.getCategory()));
		return coupRepo.save(coupon);
	}

	@Override
	public void deleteCoupon(long couponId, long companyId) throws DoesNotExistByIDException {

		if (!coupRepo.findByIdAndCompanyId(couponId, companyId).isPresent()) {
			throw new DoesNotExistByIDException("Coupon", couponId);
		}
		Coupon coupon= coupRepo.findById(couponId).get();
		Company company = compRepo.findById(companyId).get();
		coupon.setCompany(null);
		coupon.setCustomer(null);
		coupRepo.save(coupon);
		coupRepo.deleteById(couponId);
		
		
		logRepo.save(new Logger(ActionsType.DELETE,
				ClientType.COUPON,
				couponId,
				ClientType.COMPANY,
				company.getName()+", company ID-"+ company.getId(),
				"--",
				coupon.getTitle()+", "+coupon.getDescription()+", "+coupon.getCategory()));
	}

	@Override
	public List<Coupon> getCompanyCoupons(long companyId) throws MessageException {

		List<Coupon> coupons = coupRepo.findByCompanyId(companyId);
		if(coupons.size()==0)
		{
			throw new MessageException("There are no Coupons for this Company");
		}
		return coupons;
	}

	@Override
	public List<Coupon> getCompanyCouponsByCategory(Category category,long companyid) throws MessageException {

		List<Coupon> coupons =  coupRepo.findByCategoryAndCompanyId(category, companyid);
		if (coupons.size()==0) {

			throw new MessageException("There are no Coupons with this Category");
		}

		return coupons;
	}

	@Override
	public List<Coupon> getCompanyCouponsByMax(double maxPrice, long companyId) throws MessageException {

		List<Coupon> coupons = coupRepo.findByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);
		if (coupons.size()==0) {
			throw new MessageException("Coupon price does not found");
		}
		return coupons;
	}

	@Override
	public Company getCompanyDetails(long companyId) {
		return compRepo.findById(companyId).get();
	}

	/**
	 * Checks if a {@code Coupon} {@code Entity} with the same title
	 * already exists in the same {@code Company} {@code Entity} based on the
	 * {@code companyId}
	 * 
	 * @param coupon
	 * @param companyId
	 * @throws MessageException
	 * @throws IsNullException			if the {@code Coupon} value is
	 *                                        {@code null}.
	 */
	private void checkCoupon(Coupon coupon, long companyId) throws MessageException, IsNullException {
		if (coupon==null) {
			throw new IsNullException("Coupon");
		}
		if (coupon.getPrice()<0) {
			throw new MessageException("Coupon price is not valid");
		}
		if (coupon.getAmount()<0) {
			throw new MessageException("Coupon amount is not valid");
		}

//	if (coupon.getEndDate() == null)
//		throw new MessageException(message)
		if (coupon.getEndDate().getTime()<System.currentTimeMillis()) {
			throw new MessageException("Coupon date is expired");
		}
		if (coupRepo.findByTitleIgnoreCaseAndIdNotAndCompanyId(coupon.getTitle(),coupon.getId(),companyId).isPresent()) {
			throw new MessageException("Coupon title already exist");
		}
	}
}
