package com.mobile.application.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.model.Item;
import com.mobile.application.service.ItemServices;

@Controller
@RequestMapping("/User")
@ResponseBody

public class SearchController {

	@Autowired
	private ItemServices itemService;
	Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired
	ModelMapper modelMapper;

	/**
	 * Searching Item Operations
	 * 
	 * @param searchItem
	 * @param pageNumber
	 * @param size
	 * @param sort
	 * @return ItemDto
	 */
	@GetMapping("/search/{searchItem}")
	public Page<ItemDto> searchItemOpr(@PathVariable String searchItem,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {

		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		if (Objects.isNull(searchItem)) {
			logger.warn("Enter correct Item name");
			throw new ItemNotfoundException("Item : " + searchItem + "cannot be null");
		}
		Page<Item> items = null;
		Page<Item> pages = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
		items = itemService.getAllItems(pageable);
		List<Item> item = items.getContent();
		List<Item> result = new ArrayList<Item>();
		for (var list : item) {
			if ((list.getItemname().contains(searchItem.toUpperCase()))
					|| (list.getItemtype().contains(searchItem.toLowerCase()))) {
				result.add(list);
			}
		}
		pages = new PageImpl<>(result);
		if (Objects.isNull(pages)) {
			logger.error("error found in Search Operation");
			throw new ItemNotfoundException("Item : " + searchItem + "not Found");
		}
		logger.info("SearchController searchItemOpr() response{}", pages);

		return pages.map(product -> {
			return modelMapper.map(product, ItemDto.class);
		});
	}

}