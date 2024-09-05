package com.manoj.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.model.Address;
import com.manoj.model.Cart;
import com.manoj.model.CartItem;
import com.manoj.model.Order;
import com.manoj.model.OrderItem;
import com.manoj.model.Restaurant;
import com.manoj.model.User;
import com.manoj.repository.AddressRepository;
import com.manoj.repository.OrderItemRepository;
import com.manoj.repository.OrderRepository;
import com.manoj.repository.UserRepository;
import com.manoj.request.OrderRequest;

@Service
public class OrderServiceImp implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception {
		Address shippingAddress = order.getDeliveryAddress();
		
		Address savedAddress = addressRepository.save(shippingAddress);
		
		if(!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
			userRepository.save(user);
		}
		
		Restaurant restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
		
		Order createdOrder = new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreateAt(new Date());
		createdOrder.setOrderStatus("Pending");
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		
		Cart cart = cartService.findCartByUserId(user.getId());
		
		List<OrderItem> orderItems = new ArrayList<>();
		
		for(CartItem cartItem : cart.getItem()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem savedItemOrderItem = orderItemRepository.save(orderItem);
			
			orderItems.add(savedItemOrderItem);
		}
		
		Long totalPrice = cartService.calculateCartTotals(cart);
		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrice(totalPrice);
		
        Order savedOrder = orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
		return createdOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order = findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DELIVERY") || orderStatus.equals("DELIVERED") || orderStatus.equals("COMPLETED") || orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("Please select a valid order Status");
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		Order order = findOrderById(orderId);
		orderRepository.deleteById(orderId);
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
		List<Order>  orders = orderRepository.findByRestaurantId(restaurantId);
		
		if(orderStatus != null) {
			orders = orders.stream().filter(order -> order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if(optionalOrder.isEmpty()) {
			throw new Exception("Order not found");
		}
		return optionalOrder.get();
	} 
	

}
