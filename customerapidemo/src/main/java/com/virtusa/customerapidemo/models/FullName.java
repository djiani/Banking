package com.virtusa.customerapidemo.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name="FullName")
//@Data  //add getter, setter, tostring, ..
//@NoArgsConstructor
//@AllArgsConstructor

@Embeddable
public class FullName {


//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name="fullName_Id")
//	private long fullNameId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="middle_name")
	private String middleName;
	
	public FullName() {
		
	}

	public FullName(long fullNameId, String firstName, String lastName, String middleName) {
		super();
		//this.fullNameId = fullNameId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}
	

//	public long getFullNameId() {
//		return fullNameId;
//	}
//
//	public void setFullNameId(long fullNameId) {
//		this.fullNameId = fullNameId;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
}
