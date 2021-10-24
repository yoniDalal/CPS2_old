package com.jbc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jbc.util.ClientType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This {@code Entity} used for creating the companies in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#user
 */
@Getter
@Setter
@Entity
@ToString

public class Company extends User {

	/* attributes */

	private String name;


	@OneToMany(targetEntity = Coupon.class, mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Coupon> coupons;

	
	public Company() {
		role=ClientType.COMPANY;
	}
	
	/* constructor */
	public Company(String name, String email, String password) {
		coupons  = new ArrayList<Coupon>();
		this.name = name;
		this.email = email;
		this.password = password;
		role=ClientType.COMPANY;
	}
}
