package com.stock.beerstock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.beerstock.models.BeerModel;

@Repository
public interface BeerRepository extends JpaRepository<BeerModel, Long>{

	public Optional<BeerModel> findByName(String name);
	
}
