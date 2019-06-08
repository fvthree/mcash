package com.fvthree.micromcashusers.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.fvthree.micromcashusers.api.model.WebUsers;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
public class UserDaoTest {
	
	private static final String JOHNWICK_ONE_EMAIL 	  = "johnwickone@email.com";
	private static final String JOHNWICK_ONE_USERNAME = "johnwick-one";
	private static final String JOHNWICK_ONE_PASSWORD = "Xg51234Sf";

	private static final String JOHNWICK_TWO_EMAIL 	  = "johnwicktwo@email.com";
	private static final String JOHNWICK_TWO_USERNAME = "johnwick-two";
	private static final String JOHNWICK_TWO_PASSWORD = "KrE44sdfF";
	
	@Autowired
	private UserDao userDao;
	
	private WebUsers johnWickOne;
	private WebUsers johnWickTwo;

	@Before
	public void setUp() {
		johnWickOne = WebUsers.builder()
				.userid(1L)
				.username(JOHNWICK_ONE_USERNAME)
				.password(JOHNWICK_ONE_PASSWORD)
				.email(JOHNWICK_ONE_EMAIL)
				.created(new Date())
				.updated(new Date())
				.build();
		
		
		johnWickTwo = WebUsers.builder()
				.userid(2L)
				.username(JOHNWICK_TWO_USERNAME)
				.password(JOHNWICK_TWO_PASSWORD)
				.email(JOHNWICK_TWO_EMAIL)
				.created(new Date())
				.updated(new Date())
				.build();
	}
	
	@Test
	public void createWebUser_shouldReturnValidCustomer_whenAddingNewCustomer() {
		userDao.save(johnWickTwo);
		assertThat(johnWickTwo.getUserid()).isNotNull();

		Optional<WebUsers> result = userDao.findById(johnWickTwo.getUserid());
		assertThat(result).isPresent();
		assertThat(result.get()).hasFieldOrPropertyWithValue("username", JOHNWICK_TWO_USERNAME);
		assertThat(result.get()).hasFieldOrPropertyWithValue("email", JOHNWICK_TWO_EMAIL);
	}
	
	@Test
	public void findById_shouldReturnInvalidCustomer_ForZeroResult() {
		Optional<WebUsers> result = userDao.findById(new Random().nextLong());
		assertThat(result).isNotPresent();
	}
	
	@Test
	public void findById_shouldReturnValidCustomer_forExistingUser() {
		Optional<WebUsers> result = userDao.findById(2L);
		
		assertThat(result).isPresent();
		assertThat(result.get().getUsername()).isEqualTo(johnWickTwo.getUsername());
		assertThat(result.get().getEmail()).isEqualTo(johnWickTwo.getEmail());
	}
	
	@Test
	public void findByUsername_shouldReturnInvalidCustomer_ForZeroResult() {
		Optional<WebUsers> result = userDao.findByUsername("johnwick-three");
		assertThat(result).isNotPresent();
	}
	
	
	@Test
	public void findByUsername_shouldReturnValidCustomer_ForExistingUser() {
		userDao.save(johnWickOne);
		Optional<WebUsers> result = userDao.findByUsername(JOHNWICK_ONE_USERNAME);
		assertThat(result).isPresent();
		assertThat(result.get().getUsername()).isEqualTo(johnWickOne.getUsername());
		assertThat(result.get().getEmail()).isEqualTo(johnWickOne.getEmail());
	}
	
	@Test
	public void findAll_shouldReturnListOfUsers_forNotEmptyDatabase() {
		List<WebUsers> results = userDao.findAll();
		
		assertThat(results).isNotNull().hasSize(2);
	}
	
}
