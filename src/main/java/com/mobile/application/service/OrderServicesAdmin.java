package com.mobile.application.service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mobile.application.model.Orders;
import com.mobile.application.repository.OrdersRepository;

@Service
@Transactional
public class OrderServicesAdmin {

	@Autowired
	OrdersRepository orderRepository;

	public OrderServicesAdmin(OrdersRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public List<Orders> getAllOrders() {
		return (List<Orders>) orderRepository.findAll();
	}

	public Page<Orders> findAllOrdersById(Integer id, Pageable pageable) {
		return orderRepository.findAllOrdersById(id, pageable);
	}
	public Orders getOne(Integer valueOf) {
		return orderRepository.getOne(valueOf);
	}
	
	public void saveOrder(Orders orders) {
		orderRepository.save(orders);
	}
	public void deleteById(int orderid) {
		orderRepository.deleteById(orderid);
	}

}
