
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.CartDto;
import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.CartNotfoundException;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.exception.StockOverException;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.Cart;
import com.mobile.application.model.Item;
import com.mobile.application.model.User;
import com.mobile.application.repository.UserRepository;
import com.mobile.application.service.CartService;
import com.mobile.application.service.ItemServices;

@Controller
@RequestMapping("/User")
@ResponseBody
public class MultipleCartController {
	@Autowired
	private ItemServices itemServices;
	@Autowired
	private CartService cartServices;
	@Autowired
	private UserRepository userServices;
	@Autowired
	private ModelMapper modelMapper;
	Logger logger = LoggerFactory.getLogger(AccessoriesController.class);

	/**
	 * maps particular Item
	 * 
	 * @param modelType
	 * @return ItemDto
	 */
	@GetMapping(value = "/addCart/{modelType}")
	public ItemDto getCartData(@PathVariable("modelType") int modelType) {

		Item itemDetails = itemServices.getItemByModel(modelType);
		if (Objects.isNull(itemDetails)) {
			logger.warn("Enter correct model number");
			logger.error("error found in addCart");
			throw new ItemNotfoundException("model: " + modelType);
		}
		logger.info("MultipleCartController getCartData() response{}", itemDetails);

		ItemDto dta = modelMapper.map(itemDetails, ItemDto.class);
		return dta;

	}

	/**
	 * Saving the Item into Cart
	 * 
	 * @param cartDto
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return CartDto
	 */
	@PostMapping(value = "/saveCart")
	public Page<CartDto> saveToCart(@RequestBody CartDto cartDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";

		Cart cart = modelMapper.map(cartDto, Cart.class);
		Item itemDetails = itemServices.getItemByModel(cart.getModel());
		if (Objects.isNull(itemDetails)) {
			logger.warn("Cart Data Incorect for saveCart");
			throw new ItemNotfoundException("model: " + cart.getModel());
		}
		Page<Cart> newCart = null;
		if (itemDetails.getQuantity_available() < cart.getQuantity()) {
			logger.error("error found in SaveCart");
			throw new StockOverException("model: " + cart.getModel());
		}
		User userList = userServices.findById(cart.getId());
		if (Objects.isNull(userList)) {
			logger.error("User Data Incorect for saveCart");
			throw new UserNotfoundException("user id: " + cart.getId());
		}
		Pageable pageable = PageRequest.of(pageNumber, 15, Sort.by("cartid").descending());
		cartServices.save(cart);
		newCart = cartServices.findAllById(cart.getId(), pageable);
		logger.info("MultipleCartController saveToCart() response{}", newCart);

		if (Objects.isNull(newCart)) {
			logger.error("Error while saveCart");

			throw new CartNotfoundException("No cart Found");
		}
		return newCart.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}

	/**
	 * All cart values of particular user
	 * 
	 * @param id
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return CartDto
	 */
	@GetMapping(value = "/getCart/{id}")
	public Page<CartDto> getCartAll(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";
		Page<Cart> cart = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		if (Objects.isNull(id)) {
			logger.warn("Cart Data Incorect for getCart");
			throw new UserNotfoundException("user id: " + id);
		}
		cart = cartServices.findAllById(id, pageable);
		logger.info("MultipleCartController getCartAll() response{}", cart);

		if (Objects.isNull(cart)) {
			logger.error("Error found in get cart");
			throw new CartNotfoundException("Cart Not found");
		}
		return cart.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}

	/**
	 * Removing user desired product from cart
	 * 
	 * @param id
	 * @param cartid
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return CartDto
	 */
	@DeleteMapping("/remove/{id}/{cartid}")
	public Page<CartDto> remove(@PathVariable Integer id, @PathVariable("cartid") Integer cartid,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";
		Page<Cart> cartDetails = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		if (id == null || cartid == null) {
			logger.warn("Cart Data Incorect for remove cart");
			throw new CartNotfoundException("id or cartid is not correct");
		}
		cartServices.deleteById(cartid);
		cartDetails = cartServices.findAllById(id, pageable);
		logger.info("MultipleCartController remove() response{}", cartDetails);

		if (Objects.isNull(cartDetails)) {
			logger.error("Error found in remove cart");
			throw new CartNotfoundException("No cart Found");
		}
		return cartDetails.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}
}