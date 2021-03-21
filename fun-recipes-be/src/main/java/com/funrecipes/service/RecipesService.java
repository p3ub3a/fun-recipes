package com.funrecipes.service;

import java.util.List;

import com.funrecipes.dto.RecipeDTO;
import com.funrecipes.exception.MaxRequestsReachedException;

public interface RecipesService {
	String addRecipes(List<RecipeDTO> recipeDTO) throws MaxRequestsReachedException;
	List<RecipeDTO> getRecipes() throws MaxRequestsReachedException;
}
