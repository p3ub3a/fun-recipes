package com.funrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funrecipes.entity.Info;
import com.funrecipes.entity.Ingredient;

public interface MonitoringRepository extends JpaRepository<Info, Long> {

}
