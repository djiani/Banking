package com.virtusa.customerapidemo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.repositories.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	
	public List<Address> getAllAddresses() {
		return addressRepository.findAll();
	}
	
	public Address getAddressById(long address_Id) {
		
		return addressRepository.findById(address_Id).orElse(null);
	}
	
	
	public Address saveAddresses(Address address) {
		
		return addressRepository.save(address);
	}
	
	public void deleteAddressById(long id) {
		
		 addressRepository.deleteById(id);
	}
	

}
