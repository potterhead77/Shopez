package com.shopez.app.dao;

import com.shopez.app.entities.Cart;
import com.shopez.app.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart,Integer> {
    public List<Cart> findByUser(User user) ;
}
