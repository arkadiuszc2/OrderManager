package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.ViewOrderDto;
import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.enums.Status;
import com.mwo.OrderManager.repositories.ClientRepository;
import com.mwo.OrderManager.repositories.OrderRepository;
import com.mwo.OrderManager.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

  public ViewOrderDto createOrder(CreateOrderDto createOrderDto) {
    Client client = clientRepository.findById(createOrderDto.getClient_id())
        .orElseThrow(NoSuchElementException::new);

    List<Product> productsList = new ArrayList<>();
    List<Long> productIds = createOrderDto.getProductsIds();
    Product product;

    for (Long productId : productIds) {
      product = productRepository.findById(productId).
          orElseThrow(NoSuchElementException::new);

      if (product.getAmountInStore().equals(0L)) {
        throw new IllegalStateException("No product in store!");
      }

      productsList.add(product);
    }

    for (Product updatedProduct : productsList) {
      updatedProduct.setAmountInStore(updatedProduct.getAmountInStore()-1L);
      productRepository.save(updatedProduct);
    }
    Order order = orderRepository.save(Order.builder().client(client).products(productsList)
        .status(Status.NEW).build());

    return ViewOrderDto.builder().id(order.getId()).productsIds(createOrderDto.getProductsIds())
        .client_id(createOrderDto.getClient_id()).status(order.getStatus()).build();
  }

  public ViewOrderDto getOrderById(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
    List<Long> productIds = new ArrayList<>();
    order.getProducts().forEach((product) -> productIds.add(product.getId()));
        return ViewOrderDto.builder().id(order.getId()).client_id(order.getClient().getId()).status(order.getStatus())
            .productsIds(productIds).build();
  }

  public List<ViewOrderDto> getAllOrders(){
    List<Order> ordersList = orderRepository.findAll().stream().toList();
    List<ViewOrderDto> viewOrdersList = new ArrayList<>();
    List<Long> productsIds = new ArrayList<>();
    for(Order order : ordersList){
      for(Product product : order.getProducts()){
        productsIds.add(product.getId());
      }
      viewOrdersList.add(ViewOrderDto.builder().id(order.getId()).client_id(order.getClient().getId()).
          productsIds(productsIds).status(order.getStatus()).build());
    }
    return viewOrdersList;
  }
  public void updateOrderById(Long id, ViewOrderDto viewOrderDto) {
    if (!Objects.equals(id, viewOrderDto.getId())) {
      throw new IllegalArgumentException();
    }
    Order previousOrder = orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    List<Product> previousProductsList = previousOrder.getProducts();

    for (Product product : previousProductsList){
      product.setAmountInStore(product.getAmountInStore()+1L);
      productRepository.save(product);
    }

    Client client = clientRepository.findById(viewOrderDto.getClient_id())
        .orElseThrow(NoSuchElementException::new);



    List<Product> updatedProductsList = new ArrayList<>();
    List<Long> productIds = viewOrderDto.getProductsIds();
    Product product;

    for (Long productId : productIds) {
      product = productRepository.findById(productId).
          orElseThrow(NoSuchElementException::new);

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

    Order order = Order.builder().id(viewOrderDto.getId()).client(client).products(updatedProductsList)
        .status(viewOrderDto.getStatus()).build();


    orderRepository.save(order);
  }

  public void updateOrdersStatusById(Long id, Status status){
    Order order = orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    order.setStatus(status);
    orderRepository.save(order);
  }
  public void deleteOrderById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
    List<Product> productsList = order.getProducts();

    for (Product product : productsList){
      product.setAmountInStore(product.getAmountInStore()+1L);
      productRepository.save(product);
    }

    orderRepository.deleteById(id);
  }

}
