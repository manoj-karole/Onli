package com.manoj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
