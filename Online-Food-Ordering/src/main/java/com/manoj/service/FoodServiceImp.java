package com.manoj.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.model.Category;
import com.manoj.model.Food;
import com.manoj.model.Restaurant;
import com.manoj.repository.FoodRepository;
import com.manoj.request.CreateFoodRequest;

@Service
public class FoodServiceImp implements FoodService{

	@Autowired
	private FoodRepository foodRepository;
	
	
	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		
		Food food = new Food();
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDescription(req.getDescription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasional());
		food.setVegetarain(req.isVegetarin());
		
		Food savedFood = foodRepository.save(food);
		restaurant.getFoods().add(savedFood);
		
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		
		Food food = findFoodById(foodId);
		food.setRestaurant(null);
		foodRepository.save(food);
		
	}

	@Override
	public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegiterian, boolean isNonveg, boolean isSeasonal,
			String foodCategory) {
		
		List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
		
		if(isVegiterian) {
			
			foods = filterByVegetarian(foods,isVegiterian);
		}
		if(isNonveg) {
			foods = filterByNonveg(foods, isNonveg);
		}
		if(isSeasonal) {
			foods = filterBySeasonal(foods,isSeasonal);
		}
		if(foodCategory!=null && !foodCategory.equals("")) {
			foods = filterByCategory(foods,foodCategory);
		}
		
		return foods;
	}

	private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food ->{
			if(food.getFoodCategory()!= null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
		return foods.stream().filter(food -> food.isVegetarain()==false).collect(Collectors.toList());
	}

	private List<Food> filterByVegetarian(List<Food> foods, boolean isVegiterian) {
		
		return foods.stream().filter(food -> food.isVegetarain()==isVegiterian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		return foodRepository.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		
		Optional<Food> optionalFood = foodRepository.findById(foodId);
		
		if(optionalFood.isEmpty()) {
			throw new Exception("Food not exits");
		}
		return optionalFood.get();
	}

	@Override
	public Food updateAvailabilityStatus(Long foodId) throws Exception {
		
		Food food = findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return foodRepository.save(food);
	}

}
