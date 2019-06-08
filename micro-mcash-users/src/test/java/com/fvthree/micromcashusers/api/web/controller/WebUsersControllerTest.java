package com.fvthree.micromcashusers.api.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;
import java.util.Optional;

import com.fvthree.micromcashusers.api.model.WebUsers;
import com.fvthree.micromcashusers.api.repository.UserDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WebUsersControllerTest {
	
	private static final String JOHNWICK_ONE_USERNAME = "johnwickone";
	private static final String JOHNWICK_ONE_PASSWORD = "123456";
	private static final String JOHNWICK_ONE_EMAIL    = "johnwickone@gmail.com";
	
	private static final String JOHNWICK_TWO_USERNAME = "johnwicktwo";
	private static final String JOHNWICK_TWO_PASSWORD = "123456";
	private static final String JOHNWICK_TWO_EMAIL    = "johnwicktwo@gmail.com";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserDao mockDao;
	
	private WebUsers user;
	
    	private static final ObjectMapper om = new ObjectMapper();
	
	@Before
        public void init() {
	    user = new WebUsers(1L, JOHNWICK_ONE_USERNAME, JOHNWICK_ONE_PASSWORD, JOHNWICK_ONE_EMAIL, new Date(), new Date());
	}
	
	@Test
	public void find_by_id_isOk() throws Exception {
		
		when(mockDao.findById(1L)).thenReturn(Optional.of(user));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/user/id/1")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.userid", is(1)))
		        .andExpect(jsonPath("$.username", is(JOHNWICK_ONE_USERNAME)))
		        .andExpect(jsonPath("$.email", is(JOHNWICK_ONE_EMAIL)))
				.andReturn();

		verify(mockDao, times(1)).findById(1L);
	}
	
	@Test
	public void find_by_username_isOk() throws Exception {
		
		when(mockDao.findByUsername(JOHNWICK_ONE_USERNAME)).thenReturn(Optional.of(user));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/api/user/" + JOHNWICK_ONE_USERNAME)
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.userid", is(1)))
		        .andExpect(jsonPath("$.username", is(JOHNWICK_ONE_USERNAME)))
		        .andExpect(jsonPath("$.email", is(JOHNWICK_ONE_EMAIL)))
				.andReturn();
		
		verify(mockDao, times(1)).findByUsername(JOHNWICK_ONE_USERNAME);
	}
	
	@Test
	public void register_user() throws Exception {
		
		WebUsers johnWickTwo = WebUsers.builder()
				.userid(2L)
				.username(JOHNWICK_TWO_USERNAME)
				.password(JOHNWICK_TWO_PASSWORD)
				.email(JOHNWICK_TWO_EMAIL)
				.created(new Date())
				.updated(new Date())
				.build();
		
		when(mockDao.save(johnWickTwo)).thenReturn(johnWickTwo);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/api/user/register")
				.content(om.writeValueAsString(johnWickTwo))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		
		mockMvc.perform(request).andExpect(status().isCreated());
	}
}
