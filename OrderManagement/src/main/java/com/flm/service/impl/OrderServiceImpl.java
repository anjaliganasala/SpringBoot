package com.flm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flm.Exception.OrderItemNotFoundException;
import com.flm.Exception.OrderNotFoundException;
import com.flm.dao.OrderItemRepository;
import com.flm.dao.OrderRepository;
import com.flm.dao.ProductRepository;
import com.flm.dto.OrderItemResponseDto;
import com.flm.dto.OrderRequestDto;
import com.flm.dto.OrderResponseDto;
import com.flm.model.Order;
import com.flm.model.OrderItem;
import com.flm.model.Product;
import com.flm.service.OrderService;

@Service                                                 // ✅ This makes Spring register it as a bean
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    OrderItemRepository orderItemRepository;
    
   private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderResponseDto placeOrder(List<OrderRequestDto> orderRequestDtoList) {
    	LOGGER.trace("Order requestDto : {} " , orderRequestDtoList);
    	LOGGER.debug("Entered placeOrder method");
        Order order = new Order();
        List<OrderItem> orderItemsList = new ArrayList<>();
        order.setStatus("Ordered");

        for (OrderRequestDto orderRequestDto : orderRequestDtoList) {
        	LOGGER.debug("Processin Order Request DTO's to Order Items");
            OrderItem orderItem = new OrderItem();
            
            Product product = productRepository.findById(orderRequestDto.getProductId())
            		.orElseThrow( () -> {
            			LOGGER.error("can not find product with id : {} ",orderRequestDto.getProductId());
            			return new RuntimeException("id not found");
            		});
            if (product.getStock() >= orderRequestDto.getQuantity()) {
                orderItem.setQuantity(orderRequestDto.getQuantity());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItemsList.add(orderItem);
                productRepository.updateStock(product.getProductId(),product.getStock() - orderRequestDto.getQuantity());
            }else {
            	LOGGER.warn("Insufficient quantity only {} available",product.getStock());
            }
        }
        order.setOrderItems(orderItemsList);

        Order savedOrder = orderRepository.save(order);

        return buildOrderReposponseDtoFromOrder(savedOrder); 
    }
    

	@Override
	public ResponseEntity<OrderResponseDto> getOrderInfo(long orderId) {
		Order order = orderRepository.findById(orderId).
		                           orElseThrow(()-> new OrderNotFoundException("no order found with id "+orderId));
		
		OrderResponseDto orderReposponseDto = buildOrderReposponseDtoFromOrder(order);
		return ResponseEntity.status(HttpStatus.OK).body(orderReposponseDto);
	}
	
	@Override
	public ResponseEntity<Void> cancelItem(long orderItemId) {
		OrderItem orderItem = orderItemRepository.findById(orderItemId)
							.orElseThrow(() -> new OrderItemNotFoundException("No Order Item Found with Id : "+orderItemId));
		orderItemRepository.delete(orderItem);
		
		long productId = orderItem.getProduct().getProductId();
		int stock = orderItem.getProduct().getStock();
		productRepository.updateStock(productId, stock+orderItem.getQuantity());
		return ResponseEntity.noContent().build();
	}
    

    
	private OrderResponseDto buildOrderReposponseDtoFromOrder(Order savedOrder) {
		LOGGER.debug("Entered OrderResponseDto from order method");
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		
        orderResponseDto.setOrderId(savedOrder.getOrderId());
        orderResponseDto.setStatus(savedOrder.getStatus());

        List<OrderItemResponseDto> orderItemResponseDtoList = new ArrayList<OrderItemResponseDto>();
        double totalOrderAmount = 0;

        for (OrderItem orderItem : savedOrder.getOrderItems()) {
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
            orderItemResponseDto.setProductId(orderItem.getProduct().getProductId());
            orderItemResponseDto.setProductName(orderItem.getProduct().getProductName());
            orderItemResponseDto.setQuantity(orderItem.getQuantity());
            
            double eachProductPrice = orderItem.getProduct().getPrice() * ((100-orderItem.getProduct().getDiscount()) / 100);
			orderItemResponseDto.setEachProductPrice(eachProductPrice);
			
            double totalProductPrice = eachProductPrice * orderItem.getQuantity();
            orderItemResponseDto.setTotalProductPrice(totalProductPrice);
            
            totalOrderAmount += totalProductPrice;
            orderItemResponseDtoList.add(orderItemResponseDto);
        }
        orderResponseDto.setTotalAmount(totalOrderAmount);
        orderResponseDto.setOrderItems(orderItemResponseDtoList);
        LOGGER.debug("Returning from buildOrderResponseDtoFromOrder",orderResponseDto);
        return orderResponseDto;
	}

}
