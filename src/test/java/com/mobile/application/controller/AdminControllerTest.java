package com.mobile.application.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mobile.application.OnlineMobileStoreApplicationTests;
import com.mobile.application.model.Item;
import com.mobile.application.model.Payment;
import com.mobile.application.model.User;
import com.mobile.application.repository.ItemRepository;
import com.mobile.application.repository.PaymentRepository;
import com.mobile.application.repository.UserRepository;
import com.mobile.application.service.ItemServices;
import com.mobile.application.service.PaymentService;
import com.mobile.application.service.UserServices;

public class AdminControllerTest extends OnlineMobileStoreApplicationTests {

	@Autowired
	private UserServices userService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	ItemServices itemService;

	@MockBean
	private UserRepository userRepository;
	@MockBean
	private PaymentRepository paymentRepository;
	@MockBean
	private ItemRepository itemRepository;
	Pageable pageable = PageRequest.of(0, 10);

	@Test
	public void userList() {
		when(userRepository.findAll()).thenReturn(Stream
				.of(new User("manu@gmail.com", "Ninu3", (long) 906614460, "manu", "User"),
						new User("sonu@gmail.com", "sonu", (long) 906614460, "sonu", "User"))
				.collect(Collectors.toList()));
		assertEquals(2, userService.getAllUsers().size());

	}

	@Test
	public void orderList() {
		when(paymentRepository.findAll()).thenReturn(Stream.of(
				new Payment(101, 1001, 154, "daca", "sasa@gmail.com", "Bangal", "Bengalore", 1050, "headset", "cash"),
				new Payment(102, 1002, 155, "dacah", "hasa@gmail.com", "Bangal", "Bengalore", 1050, "headset", "cash"))
				.collect(Collectors.toList()));
		Pageable pageable = PageRequest.of(0, 100, Sort.by("orderid").ascending());
		assertEquals(2, paymentService.findAll(pageable).getSize());

	}

	@Test
	public void productList() {
		when(itemRepository.findAll()).thenReturn(Stream
				.of(new Item(1500, "Benz", "Black", 5000000, "Automated", "Benz.jpg", 1, 2, 5, "car"),
						new Item(1502, "Audi", "Black", 4000000, "Automated", "Audi.jpg", 1, 2, 5, "car"))
				.collect(Collectors.toList()));
		assertEquals(2, itemService.findAll().size());

	}

}
