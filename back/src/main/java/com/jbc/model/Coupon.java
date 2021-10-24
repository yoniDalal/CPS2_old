package com.jbc.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * This {@code Entity} used for creating the coupons in the system.
 * @author Miri Dahabany
 * @author Eden Bachner
 * @author Jonathan Dalal
 * @see beans#Company
 * @see beans#Customer
 **/

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity
public class Coupon {

	/* attributes */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
	@ManyToOne
	private Company company;

	@Enumerated(EnumType.STRING)
	private Category category;	

	private String title;

	private String description;

	private Date startDate;

	private Date endDate;

	private int amount;

	private double price;

	private String image;

	@ManyToMany
	@JoinTable(name = "customer_vs_coupon", joinColumns = @JoinColumn(name = "Coupon_Id" ), inverseJoinColumns = @JoinColumn(name = "Customer_id"))
	@JsonIgnore
	private List<Customer> customer ; 

	/* constructor */
	public Coupon(Category category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	/* toString */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", company=" + company.getId() + ", category=" + category + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}
}