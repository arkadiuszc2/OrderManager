package com.mwo.OrderManager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ViewOrderDto;
import com.mwo.OrderManager.enums.Status;
import com.mwo.OrderManager.repositories.ClientRepository;
import com.mwo.OrderManager.repositories.OrderRepository;
import com.mwo.OrderManager.repositories.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @InjectMocks
  private OrderService orderService;
  @Mock
  private ProductRepository productRepository = mock(ProductRepository.class);
  @Mock
  private ClientRepository clientRepository = mock(ClientRepository.class);
  @Mock
  private OrderRepository orderRepository = mock(OrderRepository.class);

  @AfterEach
  void cleanup() {
    Mockito.reset(clientRepository, productRepository, orderRepository);
  }

  @Test
  void createOrder_forValidClientAndValidProduct_shouldReturnCorrectOrder() {
    // Arrange
    Long client_id = 1L;
    Long product1Id = 2L;
    Long product2Id = 3L;
    Long products1Amount = 5L;
    Long product2Amount = 10L;

    CreateOrderDto createOrderDto = CreateOrderDto.builder().client_id(client_id)
        .productsIds(List.of(product1Id, product2Id)).build();

    Client client = Client.builder().id(client_id).build();

    Product product1 = Product.builder().id(product1Id).amountInStore(products1Amount).build();

    Product product2 = Product.builder().id(product2Id).amountInStore(product2Amount).build();

    Order order = Order.builder().client(client)

        .products(List.of(product1, product2)).status(Status.NEW).build();

    ViewOrderDto expected = ViewOrderDto.builder().client_id(client_id).status(Status.NEW)
        .productsIds(List.of(product1Id, product2Id)).build();

    when(clientRepository.findById(client_id)).thenReturn(Optional.of(client));
    when(productRepository.findById(product1Id)).thenReturn(Optional.of(product1));
    when(productRepository.findById(product2Id)).thenReturn(Optional.of(product2));
    when(productRepository.save(any())).thenReturn(any());
    when(orderRepository.save(order)).thenReturn(order);

    // Act
    ViewOrderDto result = orderService.createOrder(createOrderDto);

    // Assert
    assertEquals(expected, result);
  }

  @Test
  void createOrder_forInvalidClient_shouldReturnNoSuchElementException() {
    // Arrange
    Long client_id = 1L;
    Long product1Id = 2L;
    Long product2Id = 3L;

    CreateOrderDto createOrderDto = CreateOrderDto.builder().client_id(client_id)
        .productsIds(List.of(product1Id, product2Id)).build();

    when(clientRepository.findById(client_id)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NoSuchElementException.class, () -> orderService.createOrder(createOrderDto));
  }

  @Test
  void createOrder_forInvalidProduct_shouldReturnNoSuchElementException() {
    // Arrange
    Long client_id = 1L;
    Long product1Id = 2L;
    Long product2Id = 3L;

    CreateOrderDto createOrderDto = CreateOrderDto.builder().client_id(client_id)
        .productsIds(List.of(product1Id, product2Id)).build();

    when(productRepository.findById(product1Id)).thenReturn(Optional.empty());
    when(productRepository.save(any())).thenReturn(any());

    // Act and Assert
    assertThrows(NoSuchElementException.class, () -> orderService.createOrder(createOrderDto));
  }
  @ParameterizedTest
  @CsvFileSource(files = "src/test/resources/test.csv", numLinesToSkip = 1)
  void createOrder_forProductWithInvalidAmount_shouldReturnIllegalStateException(Long client_id, Long product1Id,
      Long product2Id, Long product1Amount, Long product2Amount) {
    // Arrange

    CreateOrderDto createOrderDto = CreateOrderDto.builder().client_id(client_id)
        .productsIds(List.of(product1Id, product2Id)).build();

    Client client = Client.builder().id(client_id).build();

    Product product1 = Product.builder().id(product1Id).amountInStore(product1Amount).build();

    Product product2 = Product.builder().id(product2Id).amountInStore(product2Amount).build();

    Order order = Order.builder().client(client)

        .products(List.of(product1, product2)).status(Status.NEW).build();

    when(clientRepository.findById(client_id)).thenReturn(Optional.of(client));
    when(productRepository.findById(product1Id)).thenReturn(Optional.of(product1));
    when(productRepository.findById(product2Id)).thenReturn(Optional.of(product2));
    when(productRepository.save(any())).thenReturn(any());
    when(orderRepository.save(order)).thenReturn(order);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> orderService.createOrder(createOrderDto));
  }

  @Test
  void createOrder_for2SameProductsWhenThereISOnly1InStore_shouldReturnIllegalStateException() {
    // Arrange
    Long client_id = 1L;
    Long product1Id = 2L;
    Long products1Amount = 1L;

    CreateOrderDto createOrderDto = CreateOrderDto.builder().client_id(client_id)
        .productsIds(List.of(product1Id, product1Id)).build();

    Client client = Client.builder().id(client_id).build();

    Product product1 = Product.builder().id(product1Id).amountInStore(products1Amount).build();

    Order order = Order.builder().client(client)
        .products(List.of(product1, product1)).status(Status.NEW).build();

    when(clientRepository.findById(client_id)).thenReturn(Optional.of(client));
    when(productRepository.findById(product1Id)).thenReturn(Optional.of(product1));
    when(productRepository.save(any())).thenReturn(any());
    when(orderRepository.save(order)).thenReturn(order);

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> orderService.createOrder(createOrderDto));

  }

  @Test
  void getOrderById_forNotExistingOrder_shouldReturnNoSuchElementException() {
    // Arrange
    Long orderId = 1L;
    Long clientId = 2L;
    Client client = Client.builder().id(clientId).build();
    Long productId = 3L;
    Product product = Product.builder().id(productId).build();
    Order order = Order.builder()
        .id(orderId)
        .status(Status.NEW)
        .client(client)
        .products(List.of(product))
        .build();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act
    ViewOrderDto viewOrderDto = orderService.getOrderById(orderId);
    Order result = Order.builder().id(viewOrderDto.getId()).status(viewOrderDto
        .getStatus()).client(client).products(List.of(product)).id(orderId).build();
    // Assert
    assertEquals(order, result);
  }

  @Test
  void getOrderById_forExistingOrder_shouldReturnCorrectOrder() {
    // Arrange
    long nonExistingOrderId = 4L;

    when(orderRepository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(nonExistingOrderId));
  }

  @Test
  void updateOrderStatusById_forExistingOrder_shouldCorrectlyChangeStatus() {
    // Arrange
    Long orderId = 1L;
    Status newStatus = Status.DONE;

    Order order = Order.builder()
        .id(orderId)
        .status(Status.NEW)
        .build();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    // Act
    orderService.updateOrderStatusById(orderId, newStatus);

    // Assert
    assertEquals(newStatus, order.getStatus());
  }

  @Test
  void updateOrderStatusById_forNotExistingOrder_shouldThrowNoSuchElementException() {
    // Arrange
    Long nonExistingOrderId = 4L;
    Status newStatus = Status.DONE;

    when(orderRepository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NoSuchElementException.class,
        () -> orderService.updateOrderStatusById(nonExistingOrderId, newStatus));
  }

  @ParameterizedTest
  @CsvSource({
      "1, 2, 1, 2",
      "3, 4, 5, 6"
  })
  void deleteOrderById_forExistingOrder_shouldCorrectlyDeleteOrder(Long orderId, Long product1Id,
      Long product1AmountInStore, Long expectedProduct1AmountInStore) {
    // Arrange
    Product product1 = Product.builder().id(product1Id).amountInStore(product1AmountInStore)
        .build();

    List<Product> productsList = List.of(product1);

    Order order = Order.builder()
        .id(orderId)
        .status(Status.NEW)
        .products(productsList)
        .build();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    // Act
    orderService.deleteOrderById(orderId);
    Long result = product1.getAmountInStore();
    // Assert
    verify(orderRepository).deleteById(orderId);
    assertEquals(expectedProduct1AmountInStore, result);
  }

  @Test
  void deleteOrderById_forNotExistingOrder_shouldThrowNoSuchElementException() {
    // Arrange
    long nonExistingOrderId = 4L;

    when(orderRepository.findById(nonExistingOrderId)).thenReturn(Optional.empty());

    // Act and Assert
    assertThrows(NoSuchElementException.class,
        () -> orderService.deleteOrderById(nonExistingOrderId));
  }
}