package com.jbc.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jbc.model.Admin;

/**
 * {@link com.jbc.model.Admin} {@code JpaRepository} use for the manage of the admin in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#Admin
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

	/* methods */
	public Optional<Admin> findByEmailIgnoreCaseAndPassword(String email, String password);
	
	public Optional<Admin> findByEmailIgnoreCase(String email);
}
