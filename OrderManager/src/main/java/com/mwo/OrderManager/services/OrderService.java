package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.enums.Status;
import com.mwo.OrderManager.repositories.ClientRepository;
import com.mwo.OrderManager.repositories.OrderRepository;
import com.mwo.OrderManager.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final ClientRepository clientRepository;
  private final ProductRepository productRepository;

  public Order createOrder(CreateOrderDto createOrderDto) {
    Client client = clientRepository.findById(createOrderDto.getClient_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Client with provided id does not exist"));

    List<Product> productsList = new ArrayList<>();
    List<Long> productIds = createOrderDto.getProductsIds();
    Product product;

    for (Long productId : productIds) {
      product = productRepository.findById(productId).
          orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Product with provided id does not exist"));

      if (product.getAmountInStore().equals(0L)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough products in store!");
      }

      productsList.add(product);
    }

    for (Product updatedProduct : productsList) {
      updatedProduct.setAmountInStore(updatedProduct.getAmountInStore()-1L);
      productRepository.save(updatedProduct);
    }


    Order order = Order.builder().id(createOrderDto.getId()).client(client).products(productsList)
        .status(Status.NEW).build();

    return orderRepository.save(order);
  }

  public Order getOrderById(Long id) {
    return orderRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Order with provided id does not exist"));
  }

  public void updateOrderById(Long id, CreateOrderDto createOrderDto) {
    if (!Objects.equals(id, createOrderDto.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Provided id and order id are not equal");
    }
    Order previousOrder = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Order with provided id does not exist"));
    List<Product> previousProductsList = previousOrder.getProducts();

    for (Product product : previousProductsList){
      product.setAmountInStore(product.getAmountInStore()+1L);
      productRepository.save(product);
    }

    Client client = clientRepository.findById(createOrderDto.getClient_id())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Client with provided id does not exist"));



    List<Product> updatedProductsList = new ArrayList<>();
    List<Long> productIds = createOrderDto.getProductsIds();
    Product product;

    for (Long productId : productIds) {
      product = productRepository.findById(productId).
          orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "Product with provided id does not exist"));

      if (product.getAmountInStore().equals(0L)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough products in store!");
      }

      updatedProductsList.add(product);
    }

    for (Product updatedProduct : updatedProductsList) {
      updatedProduct.setAmountInStore(updatedProduct.getAmountInStore()-1L);
      productRepository.save(updatedProduct);
    }

    for (Product updatedProduct : updatedProductsList) {
      updatedProduct.setAmountInStore(updatedProduct.getAmountInStore()-1L);
      productRepository.save(updatedProduct);
    }

    Order order = Order.builder().id(createOrderDto.getId()).client(client).products(updatedProductsList)
        .status(Status.NEW).build();


    orderRepository.save(order);
  }

  public void deleteOrderById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Order with provided id does not exist"));
    List<Product> productsList = order.getProducts();

    for (Product product : productsList){
      product.setAmountInStore(product.getAmountInStore()+1L);
      productRepository.save(product);
    }

    orderRepository.deleteById(id);
  }

}
