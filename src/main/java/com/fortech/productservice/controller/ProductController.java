package com.fortech.productservice.controller;

import com.fortech.productservice.model.Product;
import com.fortech.productservice.repository.ProductRepository;
import com.fortech.productservice.util.Message;
import com.fortech.productservice.util.StockClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Rest Controller for the Product related requests.
 */
//@CrossOrigin(origins = {"http://localhost:8081"}) //allow only request from proxy server - Zuul + Hystrix
@RestController
@RequestMapping("/product")
public class ProductController {

    /**
     * ProductRepository object.
     */
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockClient stockClient;

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
    @PostMapping(value = "/add")
    public ResponseEntity<?> addStock(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "description", required = false, defaultValue = "Fruit's are awesome") String description) {

        if (productRepository.findByNameIgnoreCase(name).isEmpty()) {
            productRepository.save(new Product(name, description));
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(new Message("Product already exists with name:" + name), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Update Product Name
     *
     * @param productId      - product Id
     * @param newProductName - new product name
     */
    @PutMapping(path = "/productName/update")
    public ResponseEntity<?> changeProductName(@RequestParam(name = "productId") String productId,
                                               @RequestParam(name = "newProductName") String newProductName) {

        Product product = productRepository.findOne(productId);
        if (productRepository.findByNameIgnoreCase(newProductName).isEmpty()) {
            product.setName(newProductName);
            productRepository.save(product);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new Message("Product already exists with name:" + newProductName), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Delete a product and all stock related
     *
     * @param productId - product Id
     * @return ResponseEntity
     */
  /*  @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "productId") String productId) {

        Product productToBeDeleted = productRepository.findOne(productId);
        if (productToBeDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            ResponseEntity<?> responseEntity = stockClient.deleteStockForAProduct(productId);
            // productRepository.delete(productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    /**
     * Same thing but with RestTemplate
     */

    /**
     * Delete a product and all stock related
     *
     * @param productId - product Id
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteProduct(@RequestParam(name = "productId") String productId) {

        Product productToBeDeleted = productRepository.findOne(productId);
        if (productToBeDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            String url = "http://stock-service/stock/delete?productId=" + productId;
            ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null,
                    new ParameterizedTypeReference<ResponseEntity>() {
                    });
            productRepository.delete(productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
