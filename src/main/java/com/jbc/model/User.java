package com.jbc.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.type.TrueFalseType;
import org.hibernate.usertype.UserType;

import com.jbc.util.ClientType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true )
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class User {
	
	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	protected long id;

	protected String password;

	protected String email;
	
	@Enumerated(EnumType.STRING)
	protected ClientType role;	

	
	
}
