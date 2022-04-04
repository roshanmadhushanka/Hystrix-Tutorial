package com.sysco.cdm.client.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sysco.cdm.client.entity.Product;
import com.sysco.cdm.client.mediator.BaseRestOutboundMediator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author : ralw0871
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final BaseRestOutboundMediator baseRestOutboundMediator;

    public ProductController(BaseRestOutboundMediator baseRestOutboundMediator) {
        this.baseRestOutboundMediator = baseRestOutboundMediator;
    }

    @GetMapping("/{id}")
    @HystrixCommand(fallbackMethod = "getDefaultProduct", commandKey = "getDefaultProduct")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {
        ResponseEntity<Product> responseEntity = baseRestOutboundMediator.get("http://product-discount.com?id=" + id,
                null, Product.class, Collections.emptyMap());
        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<Product> getDefaultProduct(String id) {
        Product product = new Product();
        product.setId(id);
        product.setCategory("N/A");
        return ResponseEntity.ok(product);
    }
}
