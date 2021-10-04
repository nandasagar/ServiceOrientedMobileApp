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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.model.Item;
import com.mobile.application.service.ItemServices;

@Controller
@ResponseBody
@RequestMapping("/Admin")
public class StocksController {
	@Autowired
	ItemServices itemService;

	@Autowired
	ModelMapper modelMapper;

	Logger logger = LoggerFactory.getLogger(StocksController.class);

	/**
	 * Stocks home
	* @param model
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */

	@GetMapping(value = "/stockProducts")
	public Page<ItemDto> stockProductList(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		Page<Item> products = null;
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "model";

		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		products = itemService.getAllItems(pageable);
		if (Objects.isNull(products)) {
			logger.error("error found in productList");
			throw new ItemNotfoundException("No products Found.!");
		}
		logger.info("StocksController productList() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * Delete Specific item in Stocks
	 * @param model
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@PostMapping("/deleteStockItem/{model}")
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
		logger.info("StocksController deleteItem() response{}", products);

		return products.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});

	}

	/**
	 * Saves newly added Stock
	 * @param model
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@PostMapping(value = "/saveStockItem")
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
		logger.info("StocksController addNewProduct() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});

	}

	/**
	 * A Request to Update Specific product
	 * 
	* @param model
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@PutMapping(value = "/updateStockItem/{model}")
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
		logger.info("StocksController updateItem() response{}", products);

		return products.map(items -> {
			return modelMapper.map(items, ItemDto.class);
		});
	}
}
