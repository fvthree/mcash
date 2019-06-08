package com.fvthree.micromcashusers.api.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fvthree.micromcashusers.api.model.WebUsers;
import com.fvthree.micromcashusers.api.utils.DateConverter;
import com.fvthree.micromcashusers.api.utils.PasswordUtil;

@Repository
public class UserDao {

	private static final String USER_ID  = "user_id";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String EMAIL 	 = "email";
	private static final String CREATED  = "created";
	private static final String UPDATED  = "updated";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<WebUsers> findAll() {
		final String findAllQuery = "select * from web_user";

		return jdbcTemplate.query(findAllQuery, new WebUsersMapper());
	}

	public Optional<WebUsers> findById(final Long id) {
		final String findById = "select * from web_user where user_id = ?";

        return jdbcTemplate.query(findById, 
        		rs -> rs.next() ? Optional.of(new WebUsersMapper().mapRow(rs, 1)) : Optional.empty(), id);
    }
	
	public Optional<WebUsers> findByUsername(final String username) {
		final String findByUsername = "select * from web_user where username = ?";
		
        return jdbcTemplate.query(findByUsername, 
        		rs -> rs.next() ? Optional.of(new WebUsersMapper().mapRow(rs, 1)) : Optional.empty(), username);
        }

	public boolean delete(Long id) {
		final String deleteQuery = "delete from web_user where user_id = ?";
		final Object[] params = new Object[]{id};
        
		return this.jdbcTemplate.update(deleteQuery, params) == 1;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public WebUsers save(WebUsers entity) {
        final String createQuery = "INSERT INTO web_user (username, password, email, created, updated)" +
                " VALUES (?, ?, ?, ?, ?);";
        
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUsername());
            statement.setString(2, PasswordUtil.encode(entity.getPassword()));
            statement.setString(3, entity.getEmail());
            statement.setString(4, DateConverter.toDateString(entity.getCreated()));
            statement.setString(5, DateConverter.toDateString(entity.getUpdated()));
            return statement;
        }, keyHolder);
        
        Long id = keyHolder.getKey().longValue();
        entity.setUserid(id);
        return entity;
    }

	public boolean update(final WebUsers entity) {
		final String updateQuery = "UPDATE web_user set "
				+ "username =?, email =?, password =?, updated =? where user_id =?";
		Object[] params = new Object[]{entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getUpdated(), entity.getUserid()};
		return this.jdbcTemplate.update(updateQuery, params) == 1;
	}


	class WebUsersMapper implements RowMapper<WebUsers> {
		@Override
		public WebUsers mapRow(ResultSet result, int i) throws SQLException {
			final WebUsers user = new WebUsers();
			user.setUserid(result.getLong(USER_ID));
			user.setUsername(result.getString(USERNAME));
			user.setPassword(result.getString(PASSWORD));
			user.setEmail(result.getString(EMAIL));
			user.setCreated(DateConverter.toDate(result.getString(CREATED)));
			user.setUpdated(DateConverter.toDate(result.getString(UPDATED)));
			return user;	
		}
	}
}
