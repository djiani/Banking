package com.virtusa.customerapidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virtusa.customerapidemo.models.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
