package com.flm.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flm.dto.OrderRequestDto;
import com.flm.dto.OrderResponseDto;

public interface OrderService {
	
	public OrderResponseDto placeOrder(List<OrderRequestDto> orderRequestDto) ;

	public ResponseEntity<OrderResponseDto> getOrderInfo(long orderId);

	public Object cancelItem(long orderItemId);
	

}
