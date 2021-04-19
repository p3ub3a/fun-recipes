package com.funrecipesspringaop;

import java.util.ArrayList;
import java.util.List;

import com.funrecipesspringaop.dto.IngredientDTO;
import com.funrecipesspringaop.dto.RecipeDTO;
import com.funrecipesspringaop.exception.MaxRequestsReachedException;
import com.funrecipesspringaop.service.RecipesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FunRecipesSpringaopApplication {

	private RecipesService recipeService;
	
	@Autowired
	FunRecipesSpringaopApplication(RecipesService recipesService){
		this.recipeService = recipesService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FunRecipesSpringaopApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void addDefaultRecipes() throws MaxRequestsReachedException {
		List<RecipeDTO> recipeDTOs = new ArrayList<RecipeDTO>();
		List<IngredientDTO> ingredientDTOs = new ArrayList<IngredientDTO>();
		ingredientDTOs.add(new IngredientDTO("eggs", 4));
		ingredientDTOs.add(new IngredientDTO("mascarpone", 500));
		ingredientDTOs.add(new IngredientDTO("lady fingers", 300));
		ingredientDTOs.add(new IngredientDTO("sugar", 100));
		ingredientDTOs.add(new IngredientDTO("coffee", 300));
		ingredientDTOs.add(new IngredientDTO("cocoa powder", 2));
		
		RecipeDTO recipe1 = new RecipeDTO();
		recipe1.setName("Tiramisu");
		recipe1.setDescription("Tiramisu is an Italian dessert made of savoiardi ladyfingers soaked in coffee, arranged in layers and filled with a cream made with mascarpone and eggs.");
		recipe1.setImagePath("https://www.fifteenspatulas.com/wp-content/uploads/2012/11/Tiramisu-Fifteen-Spatulas-1-640x424.jpg");
		recipe1.setIngredients(ingredientDTOs);
		recipeDTOs.add(recipe1);
		
		recipeService.addRecipes(recipeDTOs);
	}

}
