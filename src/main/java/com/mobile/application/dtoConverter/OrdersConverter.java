package com.mobile.application.dtoConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.mobile.application.dto.OrdersDto;
import com.mobile.application.model.Orders;

@Component
public class OrdersConverter {

	
	public OrdersDto entityToDto(Orders order)
	{	
		OrdersDto dto=new OrdersDto();
		dto.setOrderid(order.getOrderid());
		dto.setEmail(order.getEmail());
		dto.setId(order.getId());
		dto.setItemname(order.getItemname());
		dto.setModel(order.getModel());
		dto.setQuantity(order.getQuantity());
		dto.setAddress(order.getAddress());
		dto.setTotal(order.getTotal());
		
		return dto;
		
	}
	/**  entity page To Dto page converter
 	 * @param item
	 * @return
	 */
	public Page<OrdersDto> entityToDto(Page<Orders> cart)
	{
		
		List<OrdersDto> list= cart.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
		Page <OrdersDto>pages = new PageImpl<>(list);
		return pages;
	}
	
	/** dtoToEntity Converter
	 * @param item
	 * @return
	 */
	public Orders dtoToEntity(OrdersDto dto)
	{
		
		Orders orders=new Orders();
		orders.setOrderid(dto.getOrderid());
		orders.setEmail(dto.getEmail());
		orders.setId(dto.getId());
		orders.setItemname(dto.getItemname());
		orders.setModel(dto.getModel());
		orders.setQuantity(dto.getQuantity());
		orders.setAddress(dto.getAddress());
		orders.setTotal(dto.getTotal());
		
		
		return orders;
		
	}
	/** dto page To entity page converter
	 * @param dto
	 * @return
	 */
	public Page <Orders> dtoToEntity(Page<OrdersDto> dto)
	{	
		List<Orders> res= dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
		
		Page <Orders>pages = new PageImpl<>(res);
		return pages;
	}


	
}
