package com.manoj.request;

import com.manoj.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

	private Long restaurantId;
	private Address deliveryAddress;
}
