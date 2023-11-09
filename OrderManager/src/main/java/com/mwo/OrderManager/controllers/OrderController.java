package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.ViewOrderDto;
import com.mwo.OrderManager.enums.Status;
import com.mwo.OrderManager.services.OrderService;
import java.util.List;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ViewOrderDto createProduct(@RequestBody CreateOrderDto createOrderDto){
    ViewOrderDto viewOrderDto;
    try{
      viewOrderDto = orderService.createOrder(createOrderDto);
    }
    catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client or product with provided id does not exist");
    } catch(IllegalStateException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided product is not available in the store");
    }
    return viewOrderDto;
  }
  @GetMapping("{id}")
  public ViewOrderDto getOrderById(@PathVariable Long id){
    ViewOrderDto viewOrderDto;
    try{
      viewOrderDto = orderService.getOrderById(id);
    }
    catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Order with provided id does not exist");
    }
    return viewOrderDto;
  }
  @GetMapping
  public List<ViewOrderDto> getAllOrders(){
    return orderService.getAllOrders();
  }
  @PutMapping("/{id}")
  public void updateOrderById(@PathVariable Long id, @RequestBody ViewOrderDto viewOrderDto){
    try{
      orderService.updateOrderById(id, viewOrderDto);
    }
    catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Client,product or order with provided id does not exist");
    } catch (IllegalArgumentException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Provided id and order_id are not equal");
    }
  }
  @PutMapping("/{id}/{status}")
  public void updateOrderStatusById(@PathVariable Long id, @PathVariable Status status){
    try{
      orderService.updateOrdersStatusById(id, status);
    }
    catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Order with provided id does not exist");
    }
  }
  @DeleteMapping("/{id}")
  public void deleteOrderById(@PathVariable Long id){
    try{
      orderService.deleteOrderById(id);
    }
    catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Order with provided id does not exist");
    }
  }
}
