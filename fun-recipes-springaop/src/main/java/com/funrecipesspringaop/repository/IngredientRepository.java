package com.funrecipesspringaop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipesspringaop.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}
