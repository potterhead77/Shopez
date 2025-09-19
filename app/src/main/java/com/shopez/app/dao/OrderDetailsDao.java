package com.shopez.app.dao;

import com.shopez.app.entities.OrderDetails;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailsDao extends CrudRepository<OrderDetails, Integer> {

}
