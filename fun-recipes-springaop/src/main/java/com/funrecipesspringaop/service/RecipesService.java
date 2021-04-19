package com.funrecipesspringaop.service;

import java.util.List;

import com.funrecipesspringaop.dto.RecipeDTO;
import com.funrecipesspringaop.exception.MaxRequestsReachedException;

public interface RecipesService {
	String addRecipes(List<RecipeDTO> recipeDTO) throws MaxRequestsReachedException;
	List<RecipeDTO> getRecipes() throws MaxRequestsReachedException;
}
