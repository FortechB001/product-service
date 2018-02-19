package com.fortech.productservice.util;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "stock-service")
public interface StockClient {

    @RequestMapping(value = "/stock/delete", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStockForAProduct(@RequestParam(name = "productId") String productId);
}
