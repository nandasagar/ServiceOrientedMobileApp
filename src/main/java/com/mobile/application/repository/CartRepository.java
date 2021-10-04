package com.mobile.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.mobile.application.model.Cart;
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, Integer>{

	List<Cart> findAllByEmail(String email);


	Page<Cart> findAllById(Integer id, Pageable pageable);


	List<Cart> findAllById(Integer id);


}
