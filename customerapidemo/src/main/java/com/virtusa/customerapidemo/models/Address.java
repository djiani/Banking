package com.virtusa.customerapidemo.models;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Address")
@Data
@NoArgsConstructor
@AllArgsConstructor
//for xml flexibility
@XmlRootElement
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_Id")
	private long AddressId;
	
	@Column(name="apt_no")
	private String aptNo;
	
	@Column(name="street_name", nullable = false)
	private String streetName;
	
	@Column(name="City", nullable = false)
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zip_code")
	private String zipcode;
	
	@Column(name="country")
	private String country;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_Id")
	private Customer customer;

}
