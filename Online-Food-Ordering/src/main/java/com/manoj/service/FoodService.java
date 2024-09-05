package com.manoj.service;

import java.util.List;

import com.manoj.model.Category;
import com.manoj.model.Food;
import com.manoj.model.Restaurant;
import com.manoj.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);
	
	void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegiterian, boolean isNonveg, boolean isSeasonal, String foodCategory);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailabilityStatus(Long foodId) throws Exception;
}
