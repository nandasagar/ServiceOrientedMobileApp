package com.mobile.application.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mobile.application.model.Cart;
import com.mobile.application.model.Item;
import com.mobile.application.model.Orders;
import com.mobile.application.model.Payment;
import com.mobile.application.model.User;

public class ResponseList {

	private Page<User>user;
	
	private List<Item>item;
	
	private List<Orders>orders;
	
	private List<Cart>cart;
	
	private List<Payment>payment;

	public Page<User> getUser() {
		return user;
	}

	public void setUser(Page<User> allUsers) {
		this.user = allUsers;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public ResponseList() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	
}
