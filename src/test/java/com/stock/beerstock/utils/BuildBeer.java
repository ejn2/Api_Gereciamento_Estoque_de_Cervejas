package com.stock.beerstock.utils;

import com.stock.beerstock.models.BeerModel;

public class BuildBeer {

	public static BeerModel newBeer() {
		BeerModel beer = new BeerModel();
		beer.setId(1L);
		beer.setName("SKL");
		beer.setBrand("SKLBR");
		beer.setQuantity(100);
		
		return beer;
	}
	
}
