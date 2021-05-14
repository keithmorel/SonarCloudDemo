package com.revature.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.ShipNotFoundException;
import com.revature.model.Ship;
import com.revature.service.ShipService;

@ExtendWith(MockitoExtension.class)
public class ShipControllerTest {

	MockMvc mockMvc;
	
	@Mock
	ShipService mockShipService;
	
	@InjectMocks
	ShipController sc;
	
	@BeforeEach
	void setup() throws ShipNotFoundException {
		
		when(mockShipService.getShipById(eq(1)))
			.thenReturn(new Ship(1, "Queen Anne's Revenge"));

		this.mockMvc = MockMvcBuilders.standaloneSetup(sc).build();
	}
	
	@Test
	void testGetShipById() throws Exception {
		Ship expectedShip = new Ship(1, "Queen Anne's Revenge");
		ObjectMapper om = new ObjectMapper();
		String expected = om.writeValueAsString(expectedShip);
		this.mockMvc
			.perform(get("/ship/1"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expected));
	}

}
