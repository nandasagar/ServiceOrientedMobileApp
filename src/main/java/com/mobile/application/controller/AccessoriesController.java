package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.model.Image;
import com.mobile.application.model.Item;
import com.mobile.application.service.ItemServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@ResponseBody

public class AccessoriesController {
	
	@Autowired
	private ItemServices itemService;
	@Autowired
	private ModelMapper modelMapper;
	Image image;
	Logger log = LoggerFactory.getLogger(AccessoriesController.class);

	/**
	 * maps to Accessories page
	 * 
	 * @return
	 */
	@RequestMapping("/access")
	public String access() {
		return "/access";
	}

	/**
	 * 
	 * 
	 * maps to item->powerBank page
	 * 
	 * @return
	 */
	@GetMapping("/power")
	public Page<ItemDto> power(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "powerbank";

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController power() response{}", product);

		if (Objects.isNull(product)) {
			log.error("error found in power");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->HeadSet page
	 * 
	 * @return
	 */
	@GetMapping("/headset")
	public Page<ItemDto> headset(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "headset";
		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController headset() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in headset");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->Charger page
	 * 
	 * @return
	 */
	@GetMapping("/charger")
	public Page<ItemDto> charger(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "charger";
		log.info("AccessoriesController charger()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController charger() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in charger");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->MobileCover page
	 * 
	 * @return
	 */
	@GetMapping("/cover")
	public Page<ItemDto> cover(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "mobilecover";
		log.info("AccessoriesController cover()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController cover() response{}", product);
		if (Objects.isNull(product)) {
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			log.error("error found in cover");
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->MobileScreen page
	 * 
	 * @return
	 */
	@GetMapping("/screen")
	public Page<ItemDto> screen(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "mobilescreen";
		log.info("AccessoriesController screen()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController screen() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in mobile screen");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->USB page
	 * 
	 * @return
	 */
	@GetMapping("/usb")
	public Page<ItemDto> usb(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "usb";
		log.info("AccessoriesController usb()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController usb() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in usb");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->AppleMobile page
	 * 
	 * @return
	 */
	@GetMapping("/apple")
	public Page<ItemDto> apple(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "applemobile";
		log.info("AccessoriesController apple()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController apple() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in apple mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->VivoMobile page
	 * 
	 * @return
	 */
	@GetMapping("/vivo")
	public Page<ItemDto> vivo(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "vivomobile";
		log.info("AccessoriesController vivo()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController vivo() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in vivo mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->RealmeMobile page
	 * 
	 * @return
	 */
	@GetMapping("/realme")

	public Page<ItemDto> realme(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "realmemobile";
		log.info("AccessoriesController realme()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController realme() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in realme mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->OnePlusMobile page
	 * 
	 * @return
	 */
	@GetMapping("/oneplus")
	public Page<ItemDto> oneplus(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "oneplusmobile";
		log.info("AccessoriesController oneplus()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController oneplus() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in oneplus mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->SamsungMobile page
	 * 
	 * @return
	 */
	@GetMapping("/samsung")
	public Page<ItemDto> samsung(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "samsungmobile";
		log.info("AccessoriesController samsung()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController samsung() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in samsung mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

	/**
	 * maps to item->RedmiMobile page
	 * 
	 * @return
	 */
	@GetMapping("/mi")
	public Page<ItemDto> mi(@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 25;
		if (Objects.isNull(sort))
			sort = "model";
		Page<Item> product = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).ascending());
		String itemType = "redmimobile";
		log.info("AccessoriesController mi()");

		product = itemService.findByItemtype(itemType, pageable);
		log.info("AccessoriesController mi() response{}", product);
		if (Objects.isNull(product)) {
			log.error("error found in mi mobile");
			throw new ItemNotfoundException("No products Found.!");
		}
		return product.map(item -> {
			return modelMapper.map(item, ItemDto.class);
		});
	}

}
