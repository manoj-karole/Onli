package com.manoj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.model.Category;
import com.manoj.model.Restaurant;
import com.manoj.repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService {

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		
		Restaurant restaurant = restaurantService.getRestaurantByUserId(userId);
		
		Category category = new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
		Restaurant restaurant = restaurantService.getRestaurantByUserId(id);
		return categoryRepository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public Category findCategoryById(Long id) throws Exception {
	    Optional<Category> optionalCategory = categoryRepository.findById(id);
	    
	    if(optionalCategory.isEmpty()) {
	    	throw new Exception("Category not Found");
	    }
		return optionalCategory.get();
	}

}
