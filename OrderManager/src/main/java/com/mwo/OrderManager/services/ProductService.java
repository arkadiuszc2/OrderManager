package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.CreateProductDto;
import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ViewProductDto;
import com.mwo.OrderManager.mappings.ProductCreateMapper;
import com.mwo.OrderManager.mappings.ProductMapper;
import com.mwo.OrderManager.repositories.ProductRepository;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ProductCreateMapper productCreateMapper;

  public ViewProductDto createProduct(CreateProductDto createProductDto){
    Product product = productRepository.save(productCreateMapper.toEntity(createProductDto));
    return productMapper.toDto(product);
  }

  public ViewProductDto getProductById(Long id){
    return productRepository.findById(id).map(productMapper::toDto).orElseThrow(
        NoSuchElementException::new);
  }

  public void updateProductById(Long id, ViewProductDto viewProductDto){
    if (!Objects.equals(id, viewProductDto.getId())){
      throw new IllegalArgumentException();
    }
    Product product = productRepository.findById(id).orElseThrow(
        NoSuchElementException::new);
    product.setName(viewProductDto.getName());
    product.setPrice(viewProductDto.getPrice());
    product.setAmountInStore(viewProductDto.getAmountInStore());
    productRepository.save(product);
  }

  public void deleteProductById(Long id){
    productRepository.findById(id).orElseThrow(
        NoSuchElementException::new);
    productRepository.deleteById(id);
  }


}
