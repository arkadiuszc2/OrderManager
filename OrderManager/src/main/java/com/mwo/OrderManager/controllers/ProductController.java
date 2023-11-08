package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.ProductDto;
import com.mwo.OrderManager.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ProductDto createProduct(@RequestBody ProductDto ProductDto){
    return productService.createProduct(ProductDto);
  }
  @GetMapping("{id}")
  public ProductDto getProductById(@PathVariable Long id){
    return productService.getProductById(id);
  }
  @PutMapping("/{id}")
  public void updateProductById(@PathVariable Long id, @RequestBody ProductDto ProductDto){
    productService.updateProductById(id, ProductDto);
  }
  @DeleteMapping("/{id}")
  public void deleteProductById(@PathVariable Long id){
    productService.deleteProductById(id);
  }
}
