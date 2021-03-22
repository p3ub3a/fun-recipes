package com.funrecipes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funrecipes.dto.IngredientDTO;
import com.funrecipes.dto.RecipeDTO;
import com.funrecipes.entity.Ingredient;
import com.funrecipes.entity.Recipe;
import com.funrecipes.exception.MaxRequestsReachedException;
import com.funrecipes.repository.IngredientRepository;
import com.funrecipes.repository.RecipeRepository;

@Service
public class RecipesServiceImpl implements RecipesService{
	
	private RecipeRepository recipeRepository;
	
	private IngredientRepository ingredientRepository;
	
	@Autowired
	RecipesServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository){
		this.recipeRepository = recipeRepository;
		this.ingredientRepository = ingredientRepository;
	}

	@Transactional
	public String addRecipes(List<RecipeDTO> recipeDTOs) throws MaxRequestsReachedException{
		clearRepos();
		
		for(int i = 0; i < recipeDTOs.size(); i++) {
			Recipe recipe = new Recipe();
			
			recipe.setName(recipeDTOs.get(i).getName());
			recipe.setDescription(recipeDTOs.get(i).getDescription());
			recipe.setImagePath(recipeDTOs.get(i).getImagePath());
			
			if(recipeDTOs.get(i).getIngredients().size() == 0) throw new IllegalArgumentException("empty ingredients list for recipe name:" + recipe.getName() + "; recipe nr: " + i);
			
			for(int j = 0; j < recipeDTOs.get(i).getIngredients().size(); j++) {
				Ingredient ingredient = new Ingredient();
				
				ingredient.setName(recipeDTOs.get(i).getIngredients().get(j).getName());
				ingredient.setAmount(recipeDTOs.get(i).getIngredients().get(j).getAmount());
				ingredient.setRecipe(recipe);
				recipe.getIngredients().add(ingredient);
				ingredientRepository.save(ingredient);
			}
			recipeRepository.save(recipe);
			System.out.println("saved recipe: " + recipe.getId());
		}
		return "recipes added";
	}

	public List<RecipeDTO> getRecipes() {
		return recipeRepository.findAll().stream()
				.map(this::toRecipeDTO)
				.collect(Collectors.toList());
	}
	
	private RecipeDTO toRecipeDTO(Recipe recipe) {
		RecipeDTO recipeDto = new RecipeDTO();
		recipeDto.setName(recipe.getName());
		recipeDto.setDescription(recipe.getDescription());
		recipeDto.setImagePath(recipe.getImagePath());
		recipeDto.setIngredients(recipe.getIngredients().stream()
				.map(this::toIngredientDTO)
				.collect(Collectors.toList()));
		return recipeDto;
	}
	
	private IngredientDTO toIngredientDTO(Ingredient ingredient) {
		IngredientDTO ingredientDTO = new IngredientDTO();
		ingredientDTO.setName(ingredient.getName());
		ingredientDTO.setAmount(ingredient.getAmount());
		return ingredientDTO;
	}
	
	private void clearRepos() {
		ingredientRepository.deleteAll();
		recipeRepository.deleteAll();
	}
}
