package com.revature.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ShipDAO;
import com.revature.model.Ship;
import com.revature.model.User;
import com.revature.template.MessageTemplate;
import com.revature.template.ShipTemplate;

@ExtendWith(SpringExtension.class)
@ContextHierarchy({
	@ContextConfiguration("classpath:applicationContext.xml"),
	@ContextConfiguration("classpath:dispatcherContext.xml")
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode=ClassMode.BEFORE_CLASS) // Makes sure to create a new 'context' before running this test class
//In this case, we are making sure that the database is fully new before we run our tests
class ShipTest {
// Integration test example class
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ShipDAO shipDAO;
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.objectMapper = new ObjectMapper();
	}

	@Test
	@Transactional
	@Commit
	@Order(1)
	void testPostShip_andReceiveGoodJsonResponse() throws Exception {
		MockHttpSession session = new MockHttpSession();
		User user = new User(1, "user", "pass");
		session.setAttribute("loggedInUser", user);
		
		ShipTemplate st = new ShipTemplate("Black Pearl");
		String json = objectMapper.writeValueAsString(st);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/ship")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.session(session); // Includes the 'loggedInUser' session attribute to bypass the authorization check
		
		Ship expected = new Ship(1, "Black Pearl");
		String expectedJson = objectMapper.writeValueAsString(expected);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
	
	@Test
	@Order(2)
	void testShip1_ExistsInDB() throws Exception {
		MockHttpSession session = new MockHttpSession();
		User user = new User(1, "user", "pass");
		session.setAttribute("loggedInUser", user);
			
			
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
			.get("/ship/1")
			.session(session); // Includes the 'loggedInUser' session attribute to bypass the authorization check
			
			Ship expected = new Ship(1, "Black Pearl");
			String expectedJson = objectMapper.writeValueAsString(expected);
			
		this.mockMvc.perform(builder) // Can use this structure to get the session object out of the request (e.g. test login adding user to session)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
	
	@Test
	@Order(3)
	void testShip1000_doesNotExistInDB() throws Exception {
		MockHttpSession session = new MockHttpSession();
		User user = new User(1, "user", "pass");
		session.setAttribute("loggedInUser", user);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.get("/ship/1000")
				.session(session); // Includes the 'loggedInUser' session attribute to bypass the authorization check
		
		MessageTemplate expectedMessage = new MessageTemplate("Ship with id 1000 was not found.");
		String expected = objectMapper.writeValueAsString(expectedMessage);
		
		this.mockMvc.perform(builder)
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.content().json(expected));
	}

}
