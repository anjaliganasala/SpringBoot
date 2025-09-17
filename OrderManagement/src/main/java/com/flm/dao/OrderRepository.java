package com.flm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flm.model.Order;

@Repository                                                                // not mandy
public interface OrderRepository extends JpaRepository<Order, Long> {

	
}
