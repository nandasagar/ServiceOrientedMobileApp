package com.mobile.application.dtoConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.mobile.application.dto.ItemDto;
import com.mobile.application.model.Item;

@Component
public class ItemConverter {

	public ItemDto entityToDto(Item item)
	{
		
		ItemDto dto=new ItemDto();
		dto.setModel(item.getModel());
		dto.setItemname(item.getItemname());
		dto.setProductid(item.getProductid());
		dto.setColor(item.getColor());
		dto.setPrice(item.getPrice());
		dto.setFeatures(item.getFeatures());
		dto.setImage(item.getImage());
		dto.setCategoryid(item.getCategoryid());
		dto.setQuantity_available(item.getQuantity_available());
		dto.setItemtype(item.getItemname());
		
		
		return dto;
		
	}
	/**  entity page To Dto page converter
 	 * @param item
	 * @return
	 */
	public Page<ItemDto> entityToDto(Page<Item> item)
	{
		
		List<ItemDto> list= item.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
		Page <ItemDto>pages = new PageImpl<>(list);
		return pages;
	}
	
	/** dtoToEntity Converter
	 * @param item
	 * @return
	 */
	public Item dtoToEntity(ItemDto dto)
	{
		Item item=new Item();
		
		item.setModel(dto.getModel());
		item.setItemname(dto.getItemname());
		item.setProductid(dto.getProductid());
		item.setColor(dto.getColor());
		item.setPrice(dto.getPrice());
		item.setFeatures(dto.getFeatures());
		item.setImage(dto.getImage());
		item.setCategoryid(dto.getCategoryid());
		item.setQuantity_available(dto.getQuantity_available());
		item.setItemtype(dto.getItemname());
		return item;
		
	}
	/** dto page To entity page converter
	 * @param dto
	 * @return
	 */
	public Page <Item> dtoToEntity(Page<ItemDto> dto)
	{	
		List<Item> res= dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
		
		Page <Item>pages = new PageImpl<>(res);
		return pages;
	}

	
	
}
