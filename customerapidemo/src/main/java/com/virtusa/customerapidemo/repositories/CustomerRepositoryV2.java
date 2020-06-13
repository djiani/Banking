package com.virtusa.customerapidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtusa.customerapidemo.models.CustomerV2;

public interface CustomerRepositoryV2 extends JpaRepository<CustomerV2, Long> {

}
