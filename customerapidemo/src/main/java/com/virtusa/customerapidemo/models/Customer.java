package com.virtusa.customerapidemo.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Customer")

//for xml flexibility
@XmlRootElement

@Data  //add getter, setter, tostring, ..
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_Id")
	private long CustomerId;
	
	@ApiModelProperty(notes="Customer first name", name="first name", required=true, value="test value")
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="fullName_Id")
	@Embedded
	private FullName name;
	
	@ApiModelProperty(notes="Customer email address", name="email", required=true, value="test@gmail.com")
	@Column(name="Email", nullable = false, length = 150)
	private String email;
	
	@ApiModelProperty(notes="Customer mobile phone number", name="Mobile phone number ", required=false, value="+1 478 123 4567")
	@Column(name="mobileNo")
	private long mobileNo;
	
	
	@ApiModelProperty(notes="Customner date of birth", name="Date of Birth", required=true, value="test@gmail.com")
	@Column(name="dob")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dob;
	
//	@JsonIgnore
//	@OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
//	private Set<Address> address;
	
//	@ApiModelProperty(notes="Customner address ", name="Customer address", required=true, value="123 sara hunter Ln Nw, Georgia 31061")
//	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//	@JoinColumn(name="address_Id")
//	private Address address;
	
	
	
	
	



}
