package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
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
  public Order createProduct(@RequestBody CreateOrderDto createOrderDto){
    return orderService.createOrder(createOrderDto);
  }
  @GetMapping("{id}")
  public Order getOrderById(@PathVariable Long id){
    return orderService.getOrderById(id);
  }
  @PutMapping("/{id}")
  public void updateOrderById(@PathVariable Long id, @RequestBody CreateOrderDto createOrderDto){
    orderService.updateOrderById(id, createOrderDto);
  }
  @DeleteMapping("/{id}")
  public void deleteProductById(@PathVariable Long id){
    orderService.deleteOrderById(id);
  }
}
