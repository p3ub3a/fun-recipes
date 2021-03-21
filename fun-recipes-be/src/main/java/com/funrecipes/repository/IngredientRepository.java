package com.funrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipes.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
