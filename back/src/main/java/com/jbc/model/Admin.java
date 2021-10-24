package com.jbc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.usertype.UserType;

import com.jbc.util.ClientType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This {@code Entity} is used for creating the admins of the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#user
 */

@Getter
@Setter
@Entity(name = "admins")
@ToString

public class Admin extends User{
	
	public Admin() {
		role=ClientType.ADMIN;
	}
	
	public Admin(String password, String email) {
		this.password=password;
		this.email=email;
		role=ClientType.ADMIN;
		
	}




}
