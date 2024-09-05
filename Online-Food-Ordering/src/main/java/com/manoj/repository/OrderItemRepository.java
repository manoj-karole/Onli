package com.manoj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
