package com.stock.beerstock.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.beerstock.exceptions.BeerAlreadyExistsException;
import com.stock.beerstock.exceptions.BeerNotFoundException;
import com.stock.beerstock.models.BeerModel;
import com.stock.beerstock.repository.BeerRepository;

@Service
public class BeerService {
	
	@Autowired
	BeerRepository beerRepository;
	
	// ========================================== [ Find All] ==========================================
	
	public List<BeerModel> findAllBeers() {
		
		List<BeerModel> beerList = this.beerRepository.findAll();
		return beerList;
		
	}
	
	
	public BeerModel verifyIfExists(long id) throws BeerNotFoundException {
		return this.beerRepository.findById(id)
				.orElseThrow(() -> new BeerNotFoundException("Beer not found"));
	}
	
	
	public void verifyIfNameAlreadyExists(String name) throws BeerAlreadyExistsException {
		Optional<BeerModel> beer = this.beerRepository.findByName(name);
		if(beer.isPresent()) {
			throw new BeerAlreadyExistsException("Beer with name '"+name+"' already exist");
		}
	}
	
	// ========================================== [Find One] ==========================================
	
	public Optional<BeerModel> findOneBeer(long id) throws BeerNotFoundException {
		BeerModel beer = this.verifyIfExists(id);
		
		return Optional.of(beer);
		
	}
	
	// ========================================== [ Create ] ==========================================
	
	public BeerModel create(BeerModel newBeer) throws BeerAlreadyExistsException {
		this.verifyIfNameAlreadyExists(newBeer.getName());
		
		return this.beerRepository.save(newBeer);
		
	}
	
	// ========================================== [ update ] ==========================================
	
	public BeerModel update(BeerModel updatedBeer) throws BeerNotFoundException, BeerAlreadyExistsException{
		
		BeerModel beer = this.verifyIfExists(updatedBeer.getId());
		
		this.verifyIfNameAlreadyExists(updatedBeer.getName());
		
		beer.setName(updatedBeer.getName());
		beer.setBrand(updatedBeer.getBrand());
		beer.setQuantity(updatedBeer.getQuantity());
		
		return this.beerRepository.save(beer);
		
	}
	
	// ========================================== [ Delete ] ==========================================
	
	public void deleteBeer(Long id) throws BeerNotFoundException {
		this.verifyIfExists(id);
		
		this.beerRepository.deleteById(id);
	}
	
}
