package com.funrecipes.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.funrecipes.dto.RecipeDTO;
import com.funrecipes.exception.MaxRequestsReachedException;
import com.funrecipes.service.RecipesService;
import com.funrecipes.service.RecipesServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RecipesController {
	
	private RecipesService recipeService;
	
	@Autowired
	RecipesController(RecipesServiceImpl recipeService){
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipes")
	public List<RecipeDTO> getRecipes() throws MaxRequestsReachedException {
		return recipeService.getRecipes();
	}
//	
	@PostMapping(value = "/recipes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String storeRecipes(@RequestBody List<RecipeDTO> recipeDTOs) throws MaxRequestsReachedException {
		return JSONObject.quote(recipeService.addRecipes(recipeDTOs));
	}
}
