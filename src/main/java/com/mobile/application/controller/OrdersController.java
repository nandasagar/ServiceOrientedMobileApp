package com.mobile.application.controller;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.OrdersDto;
import com.mobile.application.exception.OrderNotfoundException;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.Cart;
import com.mobile.application.model.Item;
import com.mobile.application.model.Orders;
import com.mobile.application.model.User;
import com.mobile.application.service.CartService;
import com.mobile.application.service.ItemServices;
import com.mobile.application.service.OrdersService;
import com.mobile.application.service.UserServices;

@Controller
@RequestMapping("/User")
@ResponseBody
public class OrdersController {

	@Autowired
	private OrdersService orderServices;
	@Autowired
	private CartService cartServices;
	@Autowired
	private UserServices userServices;
	@Autowired
	private ItemServices itemServices;
	@Autowired
	private ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(OrdersController.class);

	/**
	 * Saves Users Orders from cart
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return OrderDto
	 */
	@PostMapping(value = "/saveOrders/{id}")
	public Page<OrdersDto> saveOrders(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";

		Page<Orders> orders = null;
		User userList = userServices.findById(id);
		String email = null;
		email = userList.getEmail();
		if (Objects.isNull(email)) {
			logger.warn("Enter proper user id");
			throw new UserNotfoundException("email id: " + email);
		}
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		Page<Cart> cartList = cartServices.findAllById(id, pageable);
		List<Cart> cart = cartList.getContent();
		for (var iterate : cart) {
			Orders newOrder = new Orders(id, email, iterate.getModel(), iterate.getItemname(), iterate.getQuantity(),
					iterate.getTotal(), "IN");
			orderServices.save(newOrder);
			cartServices.deleteById(iterate.getCartid());
			Item item = itemServices.getItemByModel(iterate.getModel());
			item.setQuantity_available(item.getQuantity_available() - iterate.getQuantity());
			itemServices.saveItem(item);
		}
		Pageable ordersPageable = PageRequest.of(pageNumber, size, Sort.by("orderid").descending());
		orders = orderServices.findAllOrdersById(id, ordersPageable);

		if (Objects.isNull(orders)) {
			logger.error("error found in saveOrders");
			throw new OrderNotfoundException("No Items in your Cart to order");
		}
		logger.info("OrdersController saveOrders() response{}", orders);

		return orders.map(allOrders -> {
			return modelMapper.map(allOrders, OrdersDto.class);
		});
	}

	/**
	 * Removes Users Specified Orders
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return OrderDto
	 */
	@DeleteMapping("/removeOrder/{id}/{orderid}")
	public Page<OrdersDto> remove(@PathVariable Integer id, @PathVariable("orderid") int orderid,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "orderid";
		if (Objects.isNull(id) && Objects.isNull(orderid)) {
			logger.warn("Enter correct Details");
			throw new OrderNotfoundException("id =" + id + " or orderid=" + orderid + " is incorect");
		}
		Page<Orders> orders = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		orderServices.deleteById(orderid);

		orders = orderServices.findAllOrdersById(id, pageable);
		if (Objects.isNull(orders)) {
			logger.error("error found in remove orders");
			throw new OrderNotfoundException("No Items in your Cart to order");
		}
		logger.info("OrdersController remove() response{}", orders);

		return orders.map(allOrders -> {
			return modelMapper.map(allOrders, OrdersDto.class);
		});
	}

	/**
	 * All yet to Complete Orders Every Order
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return OrderDto
	 */
	@GetMapping(value = "/getAllOrder/{id}")
	public Page<OrdersDto> getEveryOrder(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "orderid";
		Page<Orders> newOrders = null;
		if (Objects.isNull(id)) {
			logger.warn("Enter correct User id");
			throw new UserNotfoundException("User id: " + id);
		}
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		newOrders = orderServices.findAllOrdersById(id, pageable);
		if (Objects.isNull(newOrders)) {
			logger.error("error found in get every unpaid orders");
			throw new OrderNotfoundException("No Items in your Cart to order");
		}
		logger.info("OrdersController getEveryOrder() response{}", newOrders);

		return newOrders.map(allOrders -> {
			return modelMapper.map(allOrders, OrdersDto.class);
		});
	}

}
