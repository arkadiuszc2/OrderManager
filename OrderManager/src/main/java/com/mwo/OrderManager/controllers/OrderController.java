package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.OrderDto;
import com.mwo.OrderManager.services.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public OrderDto createProduct(@RequestBody OrderDto orderDto){
    return orderService.createOrder(orderDto);
  }
  @GetMapping("{id}")
  public OrderDto getOrderById(@PathVariable Long id){
    return orderService.geOrderById(id);
  }
  @PutMapping("/{id}")
  public void updateOrderById(@PathVariable Long id, @RequestBody OrderDto orderDto){
    orderService.updateOrderById(id, orderDto);
  }
  @DeleteMapping("/{id}")
  public void deleteProductById(@PathVariable Long id){
    orderService.deleteOrderById(id);
  }
}
