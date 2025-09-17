package com.flm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flm.dto.OrderRequestDto;
import com.flm.dto.OrderResponseDto;
import com.flm.service.OrderService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/orders")
public class OrderController{
	
        @Autowired
		private OrderService orderService;
		
		@PostMapping("/buy")
		@Timed(value = "orders.placed.time")
		@Counted(value = "orders.placed.count")
		public OrderResponseDto placeOrder( @RequestBody List<OrderRequestDto> orderRequestDto) {
			return orderService.placeOrder(orderRequestDto);
		}
        @GetMapping("/cancel")
		public Object cancelItem(@PathVariable (name = "orderId") long orderId , @RequestParam (name ="orderItemId") long orderItemId) {
			return orderService.cancelItem(orderItemId);
		}
		@GetMapping("/{orderId}")
		@Timed(value = "orders.placed.time")
		@Counted(value = "orders.placed.count")
		public ResponseEntity<OrderResponseDto> getOrderInfo(@PathVariable(name = "orderId") long orderId) {
			return orderService.getOrderInfo(orderId);
			
		}

	
}