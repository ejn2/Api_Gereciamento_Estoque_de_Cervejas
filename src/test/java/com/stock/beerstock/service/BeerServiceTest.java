package com.stock.beerstock.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stock.beerstock.exceptions.BeerAlreadyExistsException;
import com.stock.beerstock.exceptions.BeerNotFoundException;
import com.stock.beerstock.models.BeerModel;
import com.stock.beerstock.repository.BeerRepository;
import com.stock.beerstock.services.BeerService;
import com.stock.beerstock.utils.BuildBeer;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

	public static final Long FOUND_ID = BuildBeer.newBeer().getId();
	public static final Long NOT_FOUND_ID = 100L;
	
	@Mock
	BeerRepository beerRepository;
	
	@InjectMocks
	BeerService beerService;
	
	// =========================== [Find All] ===========================
	
	@Test
	public void whenFindAllBeersIsCalledThenShoudReturnAListOfBeer() {
		
		List<BeerModel> listBeer = new ArrayList<>();
		listBeer.add(BuildBeer.newBeer());
		
		
		when(this.beerRepository.findAll()).thenReturn(listBeer);
		
		
		List<BeerModel> foundListBeer = this.beerService.findAllBeers();
		
		
		assertThat(foundListBeer, is(not(empty())));
		assertEquals(
				listBeer.get(0).getName(),
				foundListBeer.get(0).getName());
		
	}
	
	@Test
	public void whenFindAllBeersIsCalledThenReturnAnEmptyListOfBeers() {
		
	
		when(this.beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
		
		List<BeerModel> foundListBeer = this.beerService.findAllBeers();
		
		assertThat(foundListBeer, is(empty()));
		
	}
	
	
	
	// =========================== [Find One] ===========================
	
	@Test
	public void whenFindOneBeerIsCalledWithIdValidThenReturnABeer() throws BeerNotFoundException {
		
		BeerModel beer = BuildBeer.newBeer();
		
		when(this.beerRepository.findById(beer.getId()))
			.thenReturn(Optional.of(beer));
		
		BeerModel foundBeer = this.beerService.findOneBeer(FOUND_ID).get();
		
		assertEquals(beer.getName(), foundBeer.getName());
		
	}
	
	
	@Test
	public void whenFindOneBeerIsCalledWithIdInvalidThenAExceptionIsThrown() throws BeerNotFoundException {

		
		when(this.beerRepository.findById(NOT_FOUND_ID))
			.thenReturn(Optional.empty());
			
		assertThrows(BeerNotFoundException.class, () ->
				this.beerService.findOneBeer(NOT_FOUND_ID));
		
	}

	// =========================== [Create] ===========================
	
	@Test
	public void whenCreateIsCalledWithValidsDataThenReturnNewBeer() throws BeerAlreadyExistsException {
		
		BeerModel validBeer = BuildBeer.newBeer();
		
		when(this.beerRepository.save(validBeer))
			.thenReturn(validBeer);
		
		
		assertEquals(validBeer.getName(),
				this.beerService.create(validBeer).getName());
		
	}
	
	
	@Test
	public void whenCreateIsCalledWithNameAlreadyExistsThenAExceptionShoudBeThrown() throws BeerAlreadyExistsException {
		
		BeerModel duplicatedBeer = BuildBeer.newBeer();
		
		
		when(this.beerRepository.findByName(duplicatedBeer.getName()))
			.thenReturn(Optional.of(duplicatedBeer));
		
		
		assertThrows(BeerAlreadyExistsException.class, () ->
				this.beerService.create(duplicatedBeer));
		
	}
	
	
	// =========================== [Update] ===========================
	
	@Test
	public void wheUpdateIsCalledWithValidsDataThenReturnUpdatedBeer() throws BeerNotFoundException, BeerAlreadyExistsException {
		
		BeerModel updatedBeer = BuildBeer.newBeer();
	
		
		when(this.beerRepository.findById(updatedBeer.getId()))
			.thenReturn(Optional.of(updatedBeer));
	
		
		when(this.beerRepository.save(updatedBeer))
			.thenReturn(updatedBeer);
	
		
		assertEquals(updatedBeer.getName(),
				this.beerService.update(updatedBeer).getName());
		
	}
	
	
	@Test
	public void wheUpdateIsCalledWithNameAlreadyExistsThenAnExceptionShouldBeThrown() throws BeerNotFoundException, BeerAlreadyExistsException {
		
		BeerModel alreadExistsBeer = BuildBeer.newBeer();
	
		
		when(this.beerRepository.findById(alreadExistsBeer.getId()))
			.thenReturn(Optional.of(alreadExistsBeer));
		
		
		when(this.beerRepository.findByName(alreadExistsBeer.getName()))
			.thenReturn(Optional.of(alreadExistsBeer));
		
		
		assertThrows(BeerAlreadyExistsException.class, () ->
				this.beerService.update(alreadExistsBeer));
		
	}
	
	
	@Test
	public void whenUpdatedIsCalledWithIdInvalidThenAnExceptionShouldBeThrown() {
		
		BeerModel invalidBeerId = BuildBeer.newBeer();
		
		when(this.beerRepository.findById(invalidBeerId.getId()))
			.thenReturn(Optional.empty());
		
		
		assertThrows(BeerNotFoundException.class, () ->
				this.beerService.update(invalidBeerId));
		
	}
	
	
	// =========================== [Delete] ===========================
	
	public void whenDeleteIsCalledWithIdValidThenABeerShouldBeDeleted() {
		
		BeerModel deleteBeer = BuildBeer.newBeer();
		
		
		when(this.beerRepository.findById(FOUND_ID))
			.thenReturn(Optional.of(deleteBeer));
		
		
		doNothing().when(this.beerRepository).deleteById(deleteBeer.getId());
		
		verify(this.beerRepository, times(1)).findById(deleteBeer.getId());
		verify(this.beerRepository, times(1)).deleteById(deleteBeer.getId());
		
	}
	
	
	@Test
	public void whenDeleteIscalledWithIdInvalidThenAnExceptionShouldBeThrown() {
		BeerModel deleteBeer = BuildBeer.newBeer();
		
		when(this.beerRepository.findById(deleteBeer.getId()))
			.thenReturn(Optional.empty());
		
		assertThrows(BeerNotFoundException.class, () ->
				this.beerService.deleteBeer(deleteBeer.getId()));
		
		
	}
	
}
