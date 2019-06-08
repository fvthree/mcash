package com.fvthree.micromcashusers.api.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fvthree.micromcashusers.api.model.WebUsers;
import com.fvthree.micromcashusers.api.repository.UserDao;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class WebUsersControllerRestTemplateTest {
	
	private static final String JOHNWICK_ONE_USERNAME = "johnwickone";
	private static final String JOHNWICK_ONE_PASSWORD = "123456";
	private static final String JOHNWICK_ONE_EMAIL    = "johnwickone@gmail.com";
	
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserDao mockDao;
    
    private WebUsers user;
    
    private static final ObjectMapper om = new ObjectMapper();
	
    @Before
    public void init() {
		user = new WebUsers(1L, JOHNWICK_ONE_USERNAME, JOHNWICK_ONE_PASSWORD, JOHNWICK_ONE_EMAIL, new Date(), new Date());
    }
    
    @Test
    public void find_userById_OK() throws JSONException {
    	
    	when(mockDao.findById(1L)).thenReturn(Optional.of(user));
    	
        ResponseEntity<String> response = restTemplate.getForEntity("/api/user/id/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        verify(mockDao, times(1)).findById(1L);
    }
    
    @Test
    public void find_userByUsername_OK() throws Exception {
    	
    	when(mockDao.findByUsername(JOHNWICK_ONE_USERNAME)).thenReturn(Optional.of(user));
    	
    	ResponseEntity<String> response = restTemplate.getForEntity("/api/user/"+JOHNWICK_ONE_USERNAME, String.class);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());
    	
        verify(mockDao, times(1)).findByUsername(JOHNWICK_ONE_USERNAME);

    }
    
    @Test
    public void save_user_OK() throws Exception {
    	
    	when(mockDao.save(user)).thenReturn(user);
    	    	
        ResponseEntity<String> response = restTemplate.postForEntity("/api/user/register", user, String.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(mockDao, times(1)).save(any(WebUsers.class));

    }
    
}
