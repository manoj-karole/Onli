package com.manoj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.model.Food;
import com.manoj.model.Restaurant;
import com.manoj.model.User;
import com.manoj.request.CreateFoodRequest;
import com.manoj.service.FoodService;
import com.manoj.service.RestaurantService;
import com.manoj.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
			@RequestHeader("Authorization")String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		
		
		List<Food> food = foodService.searchFood(keyword);
		
		return new ResponseEntity<>(food, HttpStatus.CREATED);
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(
			@RequestParam boolean vegitarian,
			@RequestParam boolean seasonal,
			@RequestParam boolean nonveg,
			@RequestParam(required = false) String food_category,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization")String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		
		
		List<Food> food = foodService.getRestaurantsFood(restaurantId, vegitarian, nonveg, seasonal, food_category);
		
		return new ResponseEntity<>(food, HttpStatus.OK);
	}
}
