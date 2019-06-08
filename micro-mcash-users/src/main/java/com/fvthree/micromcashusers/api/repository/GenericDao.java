package com.fvthree.micromcashusers.api.repository;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {

	List<T> findAll();

	Optional<T> findById(K id);

	boolean delete(K id);

	T save(T entity);

	boolean update(T entity);
}
