package com.mobile.application.dtoConverter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.mobile.application.dto.PaymentDto;
import com.mobile.application.model.Payment;

@Component
public class PaymentConverter {
	
	

	public PaymentDto entityToDto(Payment pay)
	{	
	
		
		PaymentDto dto=new PaymentDto();
		dto.setOrderid(pay.getOrderid());
		dto.setEmail(pay.getEmail());
		dto.setId(pay.getId());
		dto.setItemname(pay.getItemname());
		dto.setModel(pay.getModel());
		dto.setCity(pay.getCity());
		dto.setFullname(pay.getFullname());
		dto.setModeofpayment(pay.getModeofpayment());
		dto.setAddress(pay.getAddress());
		dto.setTotal(pay.getTotal());
		dto.setPaymentid(pay.getPaymentid());
		
		return dto;
		
	}
	/**  entity page To Dto page converter
 	 * @param item
	 * @return
	 */
	public Page<PaymentDto> entityToDto(Page<Payment> cart)
	{
		
		List<PaymentDto> list= cart.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
		Page <PaymentDto>pages = new PageImpl<>(list);
		return pages;
	}
	
	/** dtoToEntity Converter
	 * @param item
	 * @return
	 */
	public Payment dtoToEntity(PaymentDto dto)
	{
		
		Payment payment=new Payment();
		payment.setOrderid(dto.getOrderid());
		payment.setEmail(dto.getEmail());
		payment.setId(dto.getId());
		payment.setItemname(dto.getItemname());
		payment.setModel(dto.getModel());
		payment.setCity(dto.getCity());
		payment.setFullname(dto.getFullname());
		payment.setModeofpayment(dto.getModeofpayment());
		payment.setAddress(dto.getAddress());
		payment.setTotal(dto.getTotal());
		payment.setPaymentid(dto.getPaymentid());
		
		
		return payment;
		
	}
	/** dto page To entity page converter
	 * @param dto
	 * @return
	 */
	public Page <Payment> dtoToEntity(Page<PaymentDto> dto)
	{	
		List<Payment> res= dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
		
		Page <Payment>pages = new PageImpl<>(res);
		return pages;
	}



}
