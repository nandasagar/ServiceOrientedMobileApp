package com.mobile.application.dtoConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.mobile.application.dto.CartDto;
import com.mobile.application.model.Cart;
@Component
public class CartConverter {


	public CartDto entityToDto(Cart cart)
	{
	
		CartDto dto=new CartDto();
		dto.setCartid(cart.getCartid());
		dto.setEmail(cart.getEmail());
		dto.setId(cart.getId());
		dto.setItemname(cart.getItemname());
		dto.setModel(cart.getModel());
		dto.setQuantity(cart.getQuantity());
		dto.setPrice(cart.getPrice());
		dto.setTotal(cart.getTotal());
		
		return dto;
		
	}
	/**  entity page To Dto page converter
 	 * @param item
	 * @return
	 */
	public Page<CartDto> entityToDto(Page<Cart> cart)
	{
		
		List<CartDto> list= cart.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
		Page <CartDto>pages = new PageImpl<>(list);
		return pages;
	}
	
	/** dtoToEntity Converter
	 * @param item
	 * @return
	 */
	public Cart dtoToEntity(CartDto dto)
	{
		Cart cart=new Cart();
		
		cart.setCartid(dto.getCartid());
		cart.setEmail(dto.getEmail());
		cart.setId(dto.getId());
		cart.setItemname(dto.getItemname());
		cart.setModel(dto.getModel());
		cart.setQuantity(dto.getQuantity());
		cart.setPrice(dto.getPrice());
		cart.setTotal(dto.getTotal());
		
		return cart;
		
	}
	/** dto page To entity page converter
	 * @param dto
	 * @return
	 */
	public Page <Cart> dtoToEntity(Page<CartDto> dto)
	{	
		List<Cart> res= dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
		
		Page <Cart>pages = new PageImpl<>(res);
		return pages;
	}


	
}
