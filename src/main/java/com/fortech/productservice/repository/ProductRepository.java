package com.fortech.productservice.repository;

import com.fortech.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

    List<Product> findByNameIgnoreCase(String name);
}
