package com.virtusa.customerapidemo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.customerapidemo.models.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	//public List<Customer> findbyDob(LocalDate dob);
	
}
