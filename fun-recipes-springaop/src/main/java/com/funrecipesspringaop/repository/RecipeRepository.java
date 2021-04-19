package com.funrecipesspringaop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipesspringaop.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
