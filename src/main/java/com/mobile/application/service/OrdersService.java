package com.mobile.application.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.Orders;
import com.mobile.application.repository.OrdersRepository;

@Service
@Transactional
public class OrdersService {

	@Autowired
	private OrdersRepository orderRepository;
	
	public Page<Orders> findAllOrdersById(Integer id, Pageable pageable) {
		return orderRepository.findAllOrdersById(id, pageable);
	}
	public Orders getOne(Integer valueOf) {
		return orderRepository.getOne(valueOf);
	}
	
	public void deleteById(int orderid) {
		orderRepository.deleteById(orderid);
	}
	public void save(Orders newOrder) {
		orderRepository.save(newOrder);
	}

}
