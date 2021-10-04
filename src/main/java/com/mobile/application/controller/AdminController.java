package com.mobile.application.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.ItemDto;
import com.mobile.application.dto.PaymentDto;
import com.mobile.application.dto.UserDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.exception.OrderNotfoundException;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.Image;
import com.mobile.application.model.Item;
import com.mobile.application.model.Payment;
import com.mobile.application.model.User;
import com.mobile.application.service.ItemServices;
import com.mobile.application.service.PaymentService;
import com.mobile.application.service.UserServices;

@Controller
@RequestMapping("/Admin")
@ResponseBody
public class AdminController {

	@Autowired
	PaymentService paymentService;
	@Autowired
	ItemServices itemService;
	@Autowired
	UserServices userService;
	@Autowired
	ModelMapper modelMapper;
	Image image = new Image();
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	/**
	 * Displays all Registered Users
	 * 
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return UserDto
	 */
	@GetMapping(value = "/users")
	public Page<UserDto> userList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "id";
		Page<User> allUsers = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allUsers = userService.getAllUser(pageable);
	
		if (Objects.isNull(allUsers)) {
			logger.error("error found in userList");
			throw new UserNotfoundException("No Users Found.!");
		}
		logger.info("AdminController userList() response{}", allUsers);
		
		return allUsers.map(user -> {
			return modelMapper.map(user, UserDto.class);
		});
	}

	/**
	 * Displays All Successful Orders made by Users
	 * 
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return PaymentDto
	 */
	@GetMapping(value = "/ordersAdmin")
	public Page<PaymentDto> orderList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "paymentid";
		Page<Payment> allOrders = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		allOrders = paymentService.findAll(pageable);
		if (Objects.isNull(allOrders)) {
			logger.error("error found in orderList");
			throw new OrderNotfoundException("No Orders Found.!");
		}
		logger.info("AdminController orderList() response{}", allOrders);
		return allOrders.map(orders -> {
			return modelMapper.map(orders, PaymentDto.class);
		});
	}

	/**
	 * Displays all Products List
	 * 
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@GetMapping(value = "/products")
	public Page<ItemDto> productList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";

		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			logger.error("error found in productList");
			throw new ItemNotfoundException("No products Found.!");
		}
		logger.info("AdminController productList() response{}", products);
		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * 
	 * A product is deleted
	 *
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@DeleteMapping("/delete/{model}")
	public Page<ItemDto> deleteItem(@PathVariable("model") int model,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";
		if (Objects.isNull(model)) {
			logger.warn("Enter correct model number");
			throw new ItemNotfoundException("No products Found.!");
		}
		itemService.deleteItem(model);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			logger.error("error found in deleteItem");
			throw new ItemNotfoundException("No products Found.!");
		}
		logger.info("AdminController deleteItem() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * New Product is Saved
	 * 
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@PostMapping(value = "/saveItem")
	public Page<ItemDto> addNewProduct(@RequestBody ItemDto itemDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";

		Item item = modelMapper.map(itemDto, Item.class);
		if (Objects.isNull(item)) {
			logger.warn("Enter correct Item Details");
			throw new ItemNotfoundException("No products Saved.!");
		}
		itemService.saveItem(item);
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			logger.error("error found in addNewProduct");
			throw new ItemNotfoundException("No products Found.!");
		}
		logger.info("AdminController addNewProduct() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

	/**
	 * A Request to Update Specific product
	 * 
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@PutMapping(value = "/update/{model}")
	public Page<ItemDto> updateItem(@PathVariable int model, @RequestBody ItemDto itemDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";
		if (itemService.getItemByModel(model) != null) {

			Item item = modelMapper.map(itemDto, Item.class);
			itemService.saveItem(item);
			Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
			products = itemService.getAllItems(pageable);
		} else {
			logger.warn("Enter correct Model Number");
			throw new ItemNotfoundException("No products Found.!");
		}
		if (Objects.isNull(products)) {
			logger.error("error found in updateItem");
			throw new ItemNotfoundException("product cannot Update.!");
		}
		logger.info("AdminController updateItem() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

}
