package com.fortech.productservice.controller;

import com.fortech.productservice.model.Product;
import com.fortech.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for the Product related requests.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * ProductRepository object.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Displays all products.
     *
     * @return - the list of all products.
     */
    @GetMapping(value = "/all")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Adds a new product.
     *
     * @param name        - the name.
     * @param description - the description.
     */
    @PostMapping("/add")
    public void addStock(@RequestParam(name = "name") String name, @RequestParam(name = "description") String description) {
        productRepository.save(new Product(name, description));
    }
}
