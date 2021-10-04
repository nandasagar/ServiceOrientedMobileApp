package com.mobile.application.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.Orders;
import com.mobile.application.model.Payment;
import com.mobile.application.repository.PaymentRepository;

@Service
@Transactional

public class PaymentService {
	@Autowired

	private PaymentRepository paymentRepository1;

	private PaymentRepository paymentRepository ;
	public PaymentService(PaymentRepository paymentRepository) {		
		this.paymentRepository1 = paymentRepository;
	} 	public Payment savePayment(Payment payment, Orders order) {

		return paymentRepository1.save(payment);

	}

	public Page<Payment> findAll(Pageable pageable) {
	
		Page<Payment>payment=paymentRepository1.findAll(pageable);
		return payment;
	}

	public void save(Payment payment) {
	
	paymentRepository1.save(payment);
		
	}

	

	public Page<Payment> findAllById(Integer id, Pageable paymentPageable) {
		Page<Payment>payment= paymentRepository1.findAllById(id,paymentPageable);
		return payment;
	}
}
