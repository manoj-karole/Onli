package com.manoj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	
}
