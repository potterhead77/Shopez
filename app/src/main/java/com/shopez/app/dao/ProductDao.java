package com.shopez.app.dao;

import com.shopez.app.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends CrudRepository<Product,Integer> {  //maps product entity to productdao

}
