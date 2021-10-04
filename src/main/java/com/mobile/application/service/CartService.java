package com.mobile.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mobile.application.model.Cart;

public interface CartService {

	public List<Cart> getCart();

	public Cart findById(int id);

	public List<Cart> getCartList();

	public void addToCart(Cart cart);

	public void removefromcart(int cartid);

	public Page<Cart> findAllById(Integer id, Pageable pageable);

	public void deleteById(int cartid);

	public void save(Cart cart);

}
