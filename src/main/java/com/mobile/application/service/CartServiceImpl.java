package com.mobile.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.application.model.Cart;
import com.mobile.application.repository.CartRepository;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	CartRepository cartRepository;

	@Override
	public void addToCart(Cart cart) {
		// TODO Auto-generated method stub
		cartRepository.save(cart);

	}

	@Override
	public List<Cart> getCartList() {
			return (List<Cart>) cartRepository.findAll();
	}

	@Override
	public void removefromcart(int cartid) {
			cartRepository.deleteById(cartid);
	}

	@Override
	public List<Cart> getCart() {
			return null;
	}

	@Override
	public Cart findById(int id) {
		Cart cart = cartRepository.getOne(id);
		return cart;
	}

	@Override
	public Page<Cart> findAllById(Integer id, Pageable pageable) {
		
		return cartRepository.findAllById(id, pageable);
	}

	@Override
	public void deleteById(int cartid) {
		cartRepository.deleteById(cartid);
	}

	@Override
	public void save(Cart cart) {
		cartRepository.save(cart);
	}

}
