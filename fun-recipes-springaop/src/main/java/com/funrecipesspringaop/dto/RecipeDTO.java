package com.funrecipesspringaop.dto;

import java.util.List;

import com.funrecipesspringaop.entity.Ingredient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RecipeDTO {

	private String name;
	private String description;
	private String imagePath;
	private List<IngredientDTO> ingredients;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}
}
