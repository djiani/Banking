package com.virtusa.customerapidemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.models.CustomerV2;
import com.virtusa.customerapidemo.repositories.CustomerRepository;
import com.virtusa.customerapidemo.repositories.CustomerRepositoryV2;

@Service
public class CustomerServiceV2 {
	
	@Autowired
	private CustomerRepositoryV2 customerRepository;
	
	
	public CustomerV2 saveCustumer(CustomerV2 customer) {
		return this.customerRepository.save(customer);
	}
	
	
	public List<CustomerV2> getAllCustomers(){
		
		return this.customerRepository.findAll();
	}
	
	public CustomerV2 getCustomerById(long customerId) {
		
		return this.customerRepository.findById(customerId).orElseThrow();
	}
	
	public void deleteCustomerById(long customerId) {
			
		this.customerRepository.deleteById(customerId);
	}
	
	public List<CustomerV2> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<CustomerV2> pagedResult = customerRepository.findAll(paging);

        return pagedResult.toList();
    }
	
	
	
	
		
	

}

