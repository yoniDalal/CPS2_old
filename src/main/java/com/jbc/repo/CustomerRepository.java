package com.jbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbc.model.Customer;

/**
 * {@link com.jbc.model.Customer} {@code JpaRepository} use for the manage of the customers in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#Customer
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	/* methods */
	public Optional<Customer> findByEmailIgnoreCaseAndPassword(String email, String password);

	public Optional<Customer> findByEmailIgnoreCaseAndIdNot(String email, long id);

	public Optional<Customer>findByEmailIgnoreCase(String email);
}