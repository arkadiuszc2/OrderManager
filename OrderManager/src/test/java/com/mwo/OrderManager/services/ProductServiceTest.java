package com.mwo.OrderManager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.CreateProductDto;
import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ViewProductDto;
import com.mwo.OrderManager.entities.ViewProductDto;
import com.mwo.OrderManager.mappings.ProductCreateMapper;
import com.mwo.OrderManager.mappings.ProductCreateMapperImpl;
import com.mwo.OrderManager.mappings.ProductMapper;
import com.mwo.OrderManager.mappings.ProductMapperImpl;
import com.mwo.OrderManager.mappings.ProductMapper;
import com.mwo.OrderManager.mappings.ProductMapperImpl;
import com.mwo.OrderManager.repositories.ProductRepository;
import com.mwo.OrderManager.repositories.ProductRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductServiceTest {
  private final ProductCreateMapper productCreateMapper = new ProductCreateMapperImpl();
  private final ProductMapper productMapper = new ProductMapperImpl();
  private ProductRepository productRepository = mock(ProductRepository.class);

  private ProductService productService = new ProductService(productRepository,productMapper,productCreateMapper);

  @AfterEach
  void cleanup() {
    Mockito.reset(productRepository);
  }

  @Test
  void createProduct_forValidProductProductDto_shouldReturnCorrectProduct() {
    // Arrange
    Long productId = 1L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;
    
    Product product = Product.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();
    ViewProductDto expectedViewProductDto = ViewProductDto.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    CreateProductDto createProductDto = productCreateMapper.toDto(product);
    when(productRepository.save(any())).thenReturn(product);
    // Act
    ViewProductDto result = productService.createProduct(createProductDto);

    // Assert
    assertEquals(expectedViewProductDto, result);
  }

  @Test
  void getProductById_forExistingProduct_shouldReturnCorrectProduct() {
    // Arrange
    Long productId = 1L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    Product existingProduct = Product.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();
    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

    ViewProductDto expectedViewProductDto = ViewProductDto.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();


    // Act
    ViewProductDto result = productService.getProductById(productId);

    // Assert
    assertEquals(expectedViewProductDto, result);
  }

  @Test
  void getProductById_forNonExistingProduct_shouldThrowNoSuchElementException() {
    // Arrange
    Long nonExistingProductId = 2L;

    when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> productService.getProductById(nonExistingProductId));
  }
  @Test
  void updateProductById_forValidInput_shouldUpdateProduct() {
    // Arrange
    Long productId = 1L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    ViewProductDto updatedProductDto = ViewProductDto.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    Product existingProduct = Product.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
    when(productRepository.save(existingProduct)).thenReturn(existingProduct);
    Product expected = productMapper.toEntity(updatedProductDto);
    // Act
    productService.updateProductById(productId, updatedProductDto);

    // Assert
    assertEquals(expected, existingProduct);
  }

  @Test
  void updateProductById_forMismatchedId_shouldThrowIllegalArgumentException() {
    // Arrange
    Long productId = 1L;
    Long mismatchedId = 2L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    ViewProductDto updatedProductDto = ViewProductDto.builder()
        .id(mismatchedId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();


    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> productService.updateProductById(productId, updatedProductDto));
  }

  @Test
  void updateProductById_forNotExistingProduct_shouldThrowNoSuchElementException() {
    // Arrange
    Long productId = 5L;
    Long nonExistingProductId = 5L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    ViewProductDto updatedProductDto = ViewProductDto.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> productService.updateProductById(nonExistingProductId, updatedProductDto));
  }
  @Test
  void deleteProductById_forExistingProduct_shouldDeleteCorrectProduct() {
    // Arrange
    Long productId = 1L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    Product product = Product.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // Act
    productService.deleteProductById(productId);
    //Assert
    verify(productRepository).deleteById(productId);
  }

  @Test
  void deleteProductById_forNonExistingProduct_shouldThrowNoSuchElementException() {
    // Arrange
    Long productId = 5L;
    Long nonExistingProductId = 5L;
    String name = "product";
    double price = 10.0;
    Long amountInsStore = 3L;

    ViewProductDto updatedProductDto = ViewProductDto.builder()
        .id(productId)
        .name(name)
        .price(price)
        .amountInStore(amountInsStore)
        .build();

    when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> productService.updateProductById(nonExistingProductId, updatedProductDto));
  }

}