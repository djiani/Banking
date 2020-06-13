package com.virtusa.customerapidemo.services;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import com.virtusa.customerapidemo.models.Customer;
import com.virtusa.customerapidemo.repositories.CustomerRepository;


import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.extern.log4j.Log4j2;




@Service
@Log4j2
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	//Logger log = LogManager.getLogger(getClass());
	
			
	@CachePut(value = "customer", key="#customer.customerId")
	public Customer saveCustumer(Customer customer) {
		log.info("Update customer cache value ");
		return this.customerRepository.save(customer);
	}
	
	
	public List<Customer> getAllCustomers(){
		
		return this.customerRepository.findAll();
	}
	
	@Cacheable(cacheNames = "customer", key="#customerId", condition="#customerId>3")
	public Customer getCustomerById(long customerId) {
		
		log.info("Fetching customer  from databases!!!");
		return this.customerRepository.findById(customerId).orElse(null);
	}
	
	
	@CacheEvict(value = "customer", key="#customerId", allEntries = true)
	public void deleteCustomerById(long customerId) {
		
		log.info("Evicting Customer form the cache!");
		this.customerRepository.deleteById(customerId);
	}
	
	public List<Customer> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Customer> pagedResult = customerRepository.findAll(paging);

        return pagedResult.toList();
    }
	
	
	public List<Customer> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
 
        Page<Customer> pagedResult = customerRepository.findAll(paging);
         
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Customer>();
        }
    }
	
	
    public Page<Customer> query(String condition, Pageable pageable){
               // 1.Create the JPA Visitor
               RSQLVisitor<CriteriaQuery<Customer>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Customer>();
               // 2.Parse a RSQL into a Node
               Node rootNode = new RSQLParser().parse(condition);
               // 3.Create CriteriaQuery
               CriteriaQuery<Customer> criteriaQuery = rootNode.accept(visitor, entityManager);
               List<Customer> total = entityManager.createQuery(criteriaQuery).getResultList();
               List<Customer> resultList = entityManager.createQuery(criteriaQuery)
                       .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
       
               return new PageImpl<>(resultList,pageable, total.size());
          }


//	public Address saveCustomerAddress(Customer custumer, Address address) {
//		// TODO Auto-generated method stub
//		address.setCustomer(customer);
//		
//		return null;
//	}
  


  
	
		
	

}
