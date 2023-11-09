package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.CreateProductDto;
import com.mwo.OrderManager.entities.ViewOrderDto;
import com.mwo.OrderManager.entities.ViewProductDto;
import com.mwo.OrderManager.services.ProductService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ViewProductDto createProduct(@RequestBody CreateProductDto createProductDto){
    return productService.createProduct(createProductDto);
  }
  @GetMapping("{id}")
  public ViewProductDto getProductById(@PathVariable Long id){
    ViewProductDto viewProductDto;
    try{
      viewProductDto = productService.getProductById(id);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product with provided id does not exist");
    }
    return viewProductDto;
  }
  @PutMapping("/{id}")
  public void updateProductById(@PathVariable Long id, @RequestBody ViewProductDto ViewProductDto){
    try{
      productService.updateProductById(id, ViewProductDto);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product with provided id does not exist");
    } catch (IllegalArgumentException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Provided id and product_id are not equal");
    }
  }
  @DeleteMapping("/{id}")
  public void deleteProductById(@PathVariable Long id){
    try{
      productService.deleteProductById(id);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Product with provided id does not exist");
    }
  }
}
