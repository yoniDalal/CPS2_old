package com.jbc.repo;
/**
 * {@link com.jbc.beans.Company} {@code JpaRepository} use for the manage of the companies in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#Company
 */
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbc.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	/* methods */
	public Optional<Company> findByEmailIgnoreCaseAndPassword(String email, String password);

	public Optional<Company> findByEmailIgnoreCaseAndIdNot(String email,long companyId);

	public Optional<Company> findByNameIgnoreCaseAndIdNot( String name, long companyId);

	public Optional<Company>findByEmailIgnoreCase(String email);

}