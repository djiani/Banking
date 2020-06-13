package com.virtusa.customerapidemo.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import com.virtusa.customerapidemo.models.Address;
import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.models.CustomerV2;
import com.virtusa.customerapidemo.services.AddressService;
import com.virtusa.customerapidemo.services.CustomerService;
import com.virtusa.customerapidemo.services.CustomerServiceV2;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api/customers",  
	produces = { "application/json", "application/xml" }, 
	consumes = {"text/plain", "application/json", "application/xml"},
	headers = {"content-type=application/*"}
)
@CrossOrigin("*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerServiceV2 customerServicev2;
	
	@Autowired
	private AddressService addressService;


	
	@ApiOperation(value="Get list of customer", response = Customer.class, tags="customer-controller")
	@GetMapping()  //params="version=v1"
	public List<Customer> getAllCustumers(){
		
		return customerService.getAllCustomers();
	}
	
	
	@ApiOperation(value="Get a specific customer ", response = Customer.class)
	@GetMapping("/{customerId}")
	public Customer getCustomerById(@PathVariable long customerId) {
		
		return customerService.getCustomerById(customerId);
		
	}
	
	
	@ApiOperation(value="Register a new customer to the system in input flexibility(text, json, xml)", response = Customer.class)
	@PostMapping()
	public void saveCustomer(@RequestBody Customer customer ) {
		
		this.customerService.saveCustumer(customer);
	}
	
	
	@ApiOperation(value="Delete a specific customer in the system", response = Customer.class)
	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable int customerId) {
		
		this.customerService.deleteCustomerById(customerId);
		
	}
	
	
	//pagination
	@ApiOperation(value="retrieve customer list with pagination", response = Customer.class)
	@GetMapping("/{pageNo}/{pageSize}")
    public List<Customer> getPaginatedCustomers(@PathVariable int pageNo, 
            @PathVariable int pageSize) {

        return customerService.findPaginated(pageNo, pageSize);
    }
	
	//http://localhost:6060/customers/filters?fields=customerId,name
	@ApiOperation(value="retrieve customer list with filtering on the fields", response = Customer.class)
    @GetMapping("/filters")
    public String getFilteredCustomer(@RequestParam(name = "fields", required = false) String fields) 
    {
        List<Customer> customersList = getAllCustumers();
        ObjectMapper mapper = Squiggly.init(new ObjectMapper(), fields);  
        return SquigglyUtils.stringify(mapper, customersList);
        
    }
    
    //verionning
  //http://localhost:6060/customers?v=2
	@ApiOperation(value="retrieve customer list with versionning", response = CustomerV2.class, tags="customerv2")
    @GetMapping(params="v=2")
	public List<CustomerV2> getAllCustumersV2(){
    	System.out.println("calling from version v2");
		return customerServicev2.getAllCustomers();
	}
	
	
	//http://localhost:6060/customers/paPage?pageSize=5&pageNo=1&sortBy=email
	@ApiOperation(value="Get list of customer with pagination and sorting criteria", response = Customer.class)
	@GetMapping("/byPage")
	 public ResponseEntity<?> getAllCustomers(
	                        @RequestParam(defaultValue = "0") Integer pageNo, 
	                        @RequestParam(defaultValue = "10") Integer pageSize,
	                        @RequestParam(defaultValue = "customerId") String sortBy) 
	    {
	        List<Customer> list = customerService.getAllCustomers(pageNo, pageSize, sortBy);
	 
	        if(list.isEmpty()) {
	        	return ResponseEntity.ok("No Record found!!!");
	        }else
	        	return new ResponseEntity<List<Customer>>(list, new HttpHeaders(), HttpStatus.OK); 
	    }
	
	
	//conditional filtering
	//http://localhost:7078/customers/rsql?condition=customerId>1
	@ApiOperation(value="Appliying condition filteriing along with pagination and sportiing criteria", response = Customer.class)
	@GetMapping("/rsql")
    public Page<Customer> query(@RequestParam String condition,
                                @RequestParam(required = false,defaultValue = "0") int page,
                                @RequestParam(required = false,defaultValue = "2") int size,
                                @RequestParam(defaultValue = "CustomerId") String sortBy){
        return customerService.query(condition,PageRequest.of(page, size));
    }

	
	//association 
	//http://localhost:6060/api/customers/contact/{customer_Id}/addresses
	@ApiOperation(value="Adding a new address to a specific Customer", response = Customer.class)
	@PostMapping("/contact/{customer_Id}/addresses")
	public ResponseEntity<?> saveCustomerAddress(@PathVariable(name="customer_Id") long customer_Id,
			@RequestBody Address address){
		
		try {
			Customer customer = customerService.getCustomerById(customer_Id);   //throw exception when id not found
			address.setCustomer(customer);
			Address saveAdr = addressService.saveAddresses(address);
			return new ResponseEntity<Address>(saveAdr, new HttpHeaders(), HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok("Customer does not exits with id= "+ customer_Id);
		}
			
	}
	
	//create Get /customer/1/addresses
	//http://localhost:6060/api/customers/contact/{customer_Id}/addresses
	@ApiOperation(value="Retrieve all addresses belonging to a specific Customer", response = Customer.class)
	@GetMapping("/contact/{customer_Id}/addresses")
	public ResponseEntity<?> getAllCustomersAddresses(@PathVariable(name="customer_Id") long customer_Id) {
		
		List<Address> listAdr = addressService.getAllAddresses();
		if(!listAdr.isEmpty()) {
			List<Address> listAdrFilterd = listAdr.stream().filter(addr -> addr.getCustomer().getCustomerId() == customer_Id).collect(Collectors.toList());
			if(listAdrFilterd.isEmpty()) {
				
				return ResponseEntity.ok("Customer with ID = "+customer_Id+ " does not have any address register in file");
				
				
			}else {
				
				return new ResponseEntity<List<Address>>(listAdrFilterd, new HttpHeaders(), HttpStatus.OK);
			}
		}else {
			
			return ResponseEntity.ok("No  address register in file");
		}
		
	}
    
}
