package com.jbc.model;

import java.util.ArrayList;
import java.util.List;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jbc.util.ClientType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This {@code Entity} used for creating the customers in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#User
 * @see beans#Coupon
 **/

@Getter
@Setter
@ToString
@Entity
public class Customer extends User {

	/* attributes */


	private String firstName;

	private String lastName;



	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "customer_vs_coupon", joinColumns = @JoinColumn(name = "Customer_id" ), inverseJoinColumns = @JoinColumn(name = "Coupon_Id"))
	private List<Coupon> coupons;
	
	
	public Customer(){
		role= ClientType.CUSTOMER;
	}
	
	/* constructor */
	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		coupons=new ArrayList<>();
		role= ClientType.CUSTOMER;
	}

}