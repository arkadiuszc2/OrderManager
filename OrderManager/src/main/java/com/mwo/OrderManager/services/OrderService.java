package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.OrderDto;
import com.mwo.OrderManager.mappings.OrderMapper;
import com.mwo.OrderManager.repositories.OrderRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  public OrderDto createOrder(OrderDto orderDto){
    Order order = orderRepository.save(orderMapper.toEntity(orderDto));
    return orderMapper.toDto(order);
  }

  public OrderDto geOrderById(Long id){
    return orderRepository.findById(id).map(orderMapper::toDto).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Order with provided id does not exist"));
  }

  public void updateOrderById(Long id, OrderDto orderDto){
    if (!Objects.equals(id, orderDto.getId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided id and order id are not equal");
    }
    Order order = orderRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Product with provided id does not exist"));
    order.setClient(orderDto.getClient());
    order.setStatus(orderDto.getStatus());
    order.setProducts(orderDto.getProducts());
    orderRepository.save(order);
  }

  public void deleteOrderById(Long id){
    orderRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Client with provided id does not exist"));
    orderRepository.deleteById(id);
  }

}
