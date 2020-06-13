package com.virtusa.customerapidemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.services.AddressService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "api/addresses",
		produces = { "application/json", "application/xml" }, 
		consumes = {"text/plain", "application/json", "application/xml"},
		headers = {"content-type=application/*"})
@CrossOrigin("*")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@ApiOperation(value="List all addresses in the system", response = Address.class)
	@GetMapping
	public List<Address> getAllAddresses(){
		return addressService.getAllAddresses();
	}
	
	@ApiOperation(value="Retrieve a specific address given address_id", response = Address.class)
	@GetMapping("/{address_id}")
	public Address getAddresses(@PathVariable long address_id){
		return addressService.getAddressById(address_id);
	}

	
	@ApiOperation(value="Save a new addresses in the system", response = Address.class)
	@PostMapping
	public Address saveAddresses(@RequestBody Address address){
		return addressService.saveAddresses(address);
	}
	
	@ApiOperation(value="Delete a specific address given address-Id", response = Address.class)
	@DeleteMapping("/{address_id}")
	public void deleteAddresses(@PathVariable long address_id){
		
		addressService.deleteAddressById(address_id);
	}
}
