package com.mobile.application.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.OrdersDto;
import com.mobile.application.dto.PaymentDto;
import com.mobile.application.exception.OrderNotfoundException;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.Item;
import com.mobile.application.model.Orders;
import com.mobile.application.model.Payment;
import com.mobile.application.model.User;
import com.mobile.application.service.ItemServices;
import com.mobile.application.service.OrdersService;
import com.mobile.application.service.PaymentService;
import com.mobile.application.service.UserServices;

@Controller
@RequestMapping("/User")
@ResponseBody
public class PaymentController {

	@Autowired
	private OrdersService ordersServices;
	@Autowired
	private UserServices userService;
	@Autowired
	private ItemServices itemServices;
	@Autowired
	private PaymentService paymentServices;
	@Autowired
	ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(PaymentController.class);

	/**
	 * maps pay info page with orders details
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return PaymentDto
	 */
	@GetMapping(value = "/pay/{id}")
	public Page<OrdersDto> getDatas(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "orderid";
		if (Objects.isNull(id)) {
			logger.warn("Enter correct User id");
			throw new UserNotfoundException("User id: " + id);
		}
		Page<Orders> newOrders = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		newOrders = ordersServices.findAllOrdersById(id, pageable);
		if (Objects.isNull(newOrders)) {
			logger.error("error found in payment getDatas");
			throw new OrderNotfoundException("No Items in your Cart to order");
		}
		logger.info("PaymentController getDatas() response{}", newOrders);
		return newOrders.map(orders -> {
			return modelMapper.map(orders, OrdersDto.class);
		});
	}

	/**
	 * saves Payment Informations
	 * 
	 * @param dto
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return PaymentDto
	 */
	@PostMapping(path = "/savePay/{id}")
	public Page<PaymentDto> savePay(@RequestBody PaymentDto dto, @PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "orderid";

		Page<Payment> payList = null;
		Payment payEntity = modelMapper.map(dto, Payment.class);
		if (Objects.isNull(payEntity)) {
			logger.warn("payment Data Incorect for savePay");
			throw new OrderNotfoundException("Enter proper Payment Details");
		}
		User user = userService.findById(id);
		String email = null;
		email = user.getEmail();
		Pageable ordersPageable = PageRequest.of(pageNumber, 100, Sort.by(sort));
		Page<Orders> orderList = ordersServices.findAllOrdersById(id, ordersPageable);
		if (Objects.isNull(orderList)) {
			logger.error("error found in payment savePay orderList");
			throw new OrderNotfoundException("No Items in your Cart to order");
		}
		List<Orders> order = orderList.getContent();
		for (var iterate : order) {
			Payment payment = new Payment(id, iterate.getModel(), iterate.getOrderid(), payEntity.getFullname(), email,
					payEntity.getAddress(), payEntity.getCity(), iterate.getTotal(), iterate.getItemname(),
					payEntity.getModeofpayment());

			paymentServices.save(payment);
			Item item = itemServices.getItemByModel(iterate.getModel());
			item.setQuantity_available(item.getQuantity_available() - iterate.getQuantity());
			itemServices.saveItem(item);
		}
		int orderSize = order.size();
		Pageable paymentPageable = PageRequest.of(pageNumber, orderSize, Sort.by("paymentid").descending());
		payList = paymentServices.findAllById(id, paymentPageable);
		if (Objects.isNull(payList)) {
			logger.error("error found in payment savePay");
			throw new OrderNotfoundException("No Active orders found");
		}
		List<Orders> orderlist = new ArrayList<Orders>();
		for (var iterate : order) {
			Orders value = ordersServices.getOne(Integer.valueOf(iterate.getOrderid()));
			orderlist.add(value);
			value.setId(1);
			ordersServices.save(value);
		}
		logger.info("PaymentController savePay() response{}", payList);
		return payList.map(allOrders -> {
			return modelMapper.map(allOrders, PaymentDto.class);
		});
	}

	/**
	 * Displays All successful orders
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return PaymentDto
	 */
	@GetMapping(value = "/getOrder/{id}")
	public Page<PaymentDto> getOrderList(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "paymentid";
		Page<Payment> newOrders = null;
		if (Objects.isNull(id)) {
			logger.warn("Enter correct User id");
			throw new UserNotfoundException("User id: " + id);
		}
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		newOrders = paymentServices.findAllById(id, pageable);
		if (Objects.isNull(newOrders)) {
			logger.error("error found in payment getOrderList");
			throw new OrderNotfoundException("No Items in your Orders");
		}
		logger.info("In PaymentController.getOrderList()");
		return newOrders.map(allOrders -> {
			return modelMapper.map(allOrders, PaymentDto.class);
		});
	}
}