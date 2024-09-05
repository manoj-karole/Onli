package com.manoj.request;

import java.util.List;

import com.manoj.model.Category;
import com.manoj.model.IngredientsItem;

import lombok.Data;

@Data
public class CreateFoodRequest {

	private String name;
	private String description;
	private Long price;
	
	private Category category;
	private List<String> images;
	
	private Long restaurantId;
	private boolean vegetarin;
	private boolean seasional;
	private List<IngredientsItem> ingredients;
} 
