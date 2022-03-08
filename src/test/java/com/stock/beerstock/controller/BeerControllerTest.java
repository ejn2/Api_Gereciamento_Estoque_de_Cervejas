package com.stock.beerstock.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.beerstock.exceptions.BeerAlreadyExistsException;
import com.stock.beerstock.exceptions.BeerNotFoundException;
import com.stock.beerstock.models.BeerModel;
import com.stock.beerstock.repository.BeerRepository;
import com.stock.beerstock.services.BeerService;
import com.stock.beerstock.utils.BuildBeer;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BeerController.class)
public class BeerControllerTest {
	
	public static final String API_PATH = "/api/v1/beerstock";
	public static final Long VALID_ID = 1L;
	public static final Long INVALID_ID = 100L;
	
	@Autowired
	MockMvc mockmvc;
	
	@MockBean
	BeerRepository beerRepo;
	
	@MockBean
	BeerService beerService;
	
	@Autowired
	ObjectMapper objectMapper;

	
	//========================== [ Find All - [GET] ] ==========================
	
	@Test
	public void whenSHOWALLBEERIsCalledThenAListOfBeerIsReturned() throws Exception {
		BeerModel beer = BuildBeer.newBeer();
		
		List<BeerModel> beerList = Collections.singletonList(beer);
		
		when(this.beerService.findAllBeers())
			.thenReturn(beerList);
		
		this.mockmvc.perform(get(API_PATH)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name", is(beer.getName())))
			.andExpect(jsonPath("$[0].brand", is(beer.getBrand())))
			.andExpect(jsonPath("$[0].quantity", is(beer.getQuantity())));
		
	}
	
	
	//========================== [ Find One - [GET] ] ==========================
	
	@Test
	public void whenFINDBEERIsCalledThenABeerShouldBeReturned() throws Exception {
		
		BeerModel beer = BuildBeer.newBeer();
		
		when(this.beerService.findOneBeer(beer.getId()))
			.thenReturn(Optional.of(beer));
		
		this.mockmvc.perform(get(API_PATH + "/" + beer.getId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(beer.getName())))
			.andExpect(jsonPath("$.brand", is(beer.getBrand())))
			.andExpect(jsonPath("$.quantity", is(beer.getQuantity())));
			
		
	}
	
	
	@Test
	public void whenFINDBEERIsCalledWithInvalidIdThen404() throws Exception {
		
		
		when(this.beerService.findOneBeer(INVALID_ID))
			.thenThrow(BeerNotFoundException.class);
		
		this.mockmvc.perform(get(API_PATH + "/" + INVALID_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
			
		
	}
	
	
	
	//========================== [ Create - [POST] ] ==========================
	
    @Test
    public void whenCREATEBEERisCalledThenANewBeerShouldBeCreated() throws JsonProcessingException, Exception {
    	BeerModel newBeer = BuildBeer.newBeer();
    	
    	String body = this.objectMapper.writeValueAsString(newBeer);
    	
    	when(this.beerService.create(any(BeerModel.class)))
    		.thenReturn(newBeer);
    	
    	this.mockmvc.perform(post(API_PATH)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    	.andExpect(status().isCreated())
    	.andExpect(jsonPath("$.name", is(newBeer.getName())))
    	.andExpect(jsonPath("$.brand", is(newBeer.getBrand())))
    	.andExpect(jsonPath("$.quantity", is(newBeer.getQuantity())));
    	
    	
    }
	
    
  //========================== [ Update - [PUT] ] ==========================
    
    @Test
    public void whenUPDATEBEERIsCalledThenABeerShouldBeUpdated() throws Exception {
    	BeerModel beerUpdated = BuildBeer.newBeer();
    	
    	String body = this.objectMapper.writeValueAsString(beerUpdated);
    	
    	when(this.beerService.update(any(BeerModel.class)))
    		.thenReturn(beerUpdated);
    	
    	this.mockmvc.perform(put(API_PATH + "/" + beerUpdated.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.name", is(beerUpdated.getName())))
    		.andExpect(jsonPath("$.brand", is(beerUpdated.getBrand())))
    		.andExpect(jsonPath("$.quantity", is(beerUpdated.getQuantity())));
    	
    	
    }
    
    
    @Test
    public void whenUPDATEBEERIsCalledWhithNameAlreadyExistsThenAnExceptionShouldBeThrown() throws Exception {
    	BeerModel beerUpdated = BuildBeer.newBeer();
    	
    	String body = this.objectMapper.writeValueAsString(beerUpdated);
    	
    	when(this.beerService.update(any(BeerModel.class)))
    		.thenThrow(BeerAlreadyExistsException.class);
    	
    	this.mockmvc.perform(put(API_PATH + "/" + beerUpdated.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    		.andExpect(status().isBadRequest());
    }
    
    
    @Test
    public void whenUPDATEBEERIsCalledWhithInvalidIdThenAnExceptionShouldBeThrown() throws Exception {
    	BeerModel beerUpdated = BuildBeer.newBeer();
    	
    	String body = this.objectMapper.writeValueAsString(beerUpdated);
    	
    	when(this.beerService.update(any(BeerModel.class)))
    		.thenThrow(BeerNotFoundException.class);
    	
    	this.mockmvc.perform(put(API_PATH + "/" + INVALID_ID)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(body))
    		.andExpect(status().isNotFound());
    }
    
    
    //========================== [ Delete - [DELETE] ] ==========================
    
    @Test
    public void whenDELETEIsCalledThenABeerShouldBeDeleted() throws Exception {
    	
    	doNothing().when(this.beerService).deleteBeer(VALID_ID);
    	
    	this.mockmvc.perform(delete(API_PATH + "/" + VALID_ID)
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNoContent());
    	
    	verify(this.beerService, times(1)).deleteBeer(VALID_ID);
    	
    }
    
    
    @Test
    public void whenDELETEIsCalledWithInvalidIdThenAnExceptionShouldBeThrown() throws Exception {
    	
    	doThrow(BeerNotFoundException.class).when(this.beerService).deleteBeer(INVALID_ID);
    	
    	this.mockmvc.perform(delete(API_PATH + "/" + INVALID_ID)
    			.contentType(MediaType.APPLICATION_JSON))
    	.andExpect(status().isNotFound());
    	
    	verify(this.beerService, times(1)).deleteBeer(INVALID_ID);
    	
    }
    
    
    
    //========================== [ Validations - [POST] ] ==========================
    
    @Test
    public void whenPOSTisCalledWithInvalidsDataThenErrorMessageIsReturned() throws Exception {
    	
    	this.mockmvc.perform(post(API_PATH)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{}"))
    		.andExpect(status().isBadRequest())
    		.andExpect(jsonPath("$.errors.name", Is.is("Is mandatory.")))
    		.andExpect(jsonPath("$.errors.brand", Is.is("Is mandatory.")));
    		
    	
    }
    
    //========================== [ Validations - [PUT] ] ==========================
    
    @Test
    public void whenPUTisCalledWithInvalidsDataThenErrorMessageIsReturned() throws Exception {
    	
    	this.mockmvc.perform(put(API_PATH + "/" + INVALID_ID)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{}"))
    		.andExpect(status().isBadRequest())
    		.andExpect(jsonPath("$.errors.name", Is.is("Is mandatory.")))
    		.andExpect(jsonPath("$.errors.brand", Is.is("Is mandatory.")));
    	
    }
    
}
