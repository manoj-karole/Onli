package com.manoj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.model.IngredientCategory;
import com.manoj.model.IngredientsItem;
import com.manoj.model.Restaurant;
import com.manoj.repository.IngredientCategoryRepository;
import com.manoj.repository.IngredientItemRepository;

@Service
public class IngredientServiceImp implements IngredientService{

	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		
		IngredientCategory category = new IngredientCategory();
		
		category.setRestaurant(restaurant);
		category.setName(name);
		
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		
		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("Ingredient Category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		
		restaurantService.findRestaurantById(id);
		return ingredientCategoryRepository.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		
		Restaurant restaurant = restaurantService.findRestaurantById(categoryId);
		IngredientCategory category = findIngredientCategoryById(categoryId);
		
		
		IngredientsItem item = new IngredientsItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem ingrediente = ingredientItemRepository.save(item);
		category.getIngredients().add(ingrediente);
		return item;
	}

	@Override
	public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
		
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		
		Optional<IngredientsItem> optionIngredientsItem = ingredientItemRepository.findById(id);
		
		if(optionIngredientsItem.isEmpty()) {
			throw new Exception("ingredient not found");
		}
		IngredientsItem ingredientsItem = optionIngredientsItem.get();
		ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
		
		return ingredientItemRepository.save(ingredientsItem);
	}
	
}
