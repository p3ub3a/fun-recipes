package com.funrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipes.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
