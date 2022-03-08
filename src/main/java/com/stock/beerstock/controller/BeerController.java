package com.stock.beerstock.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stock.beerstock.exceptions.BeerAlreadyExistsException;
import com.stock.beerstock.exceptions.BeerNotFoundException;
import com.stock.beerstock.models.BeerModel;
import com.stock.beerstock.repository.BeerRepository;
import com.stock.beerstock.services.BeerService;

@RestController
@RequestMapping(path = "/api/v1/beerstock")
public class BeerController {
	
	@Autowired
	BeerService beerService;
	
	@Autowired
	BeerRepository beerRepo;
	
	// ======================== [ List of Beer [GET] ] ========================
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<BeerModel> showAllBeers() {
		return this.beerService.findAllBeers();
		
	}
	
	// ======================== [ Find one Beer [GET] ] ========================
	
	@GetMapping(path = "/{id}")
	public BeerModel findBeer(@PathVariable Long id) throws BeerNotFoundException {
		return this.beerService.findOneBeer(id).get();
	}
	
	// ======================== [ New Beer [POST] ] ========================
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BeerModel createBeer(@Valid @RequestBody BeerModel beer) throws BeerAlreadyExistsException {
		return this.beerService.create(beer);
	}
	
	// ======================== [ Update one Beer [PUT] ] ========================
	
	@PutMapping(path = "/{id}")
	public BeerModel updateBeer(@PathVariable Long id, @Valid @RequestBody BeerModel alteredBeer) throws BeerNotFoundException, BeerAlreadyExistsException {
		alteredBeer.setId(id);
		return this.beerService.update(alteredBeer);
	}
	
	// ======================== [ Delete one Beer [DELETE] ] ========================
	
	@DeleteMapping(path="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) throws BeerNotFoundException {
		this.beerService.deleteBeer(id);
	}
	
	
}
