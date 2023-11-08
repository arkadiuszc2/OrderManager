package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ProductDto;
import com.mwo.OrderManager.mappings.ProductMapper;
import com.mwo.OrderManager.repositories.ProductRepository;
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

  public ProductDto createProduct(ProductDto productDto){
    Product product = productRepository.save(productMapper.toEntity(productDto));
    return productMapper.toDto(product);
  }

  public ProductDto getProductById(Long id){
    return productRepository.findById(id).map(productMapper::toDto).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Product with provided id does not exist"));
  }

  public void updateProductById(Long id, ProductDto productDto){
    if (!Objects.equals(id, productDto.getId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided id and product id are not equal");
    }
    Product product = productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Product with provided id does not exist"));
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setAmountInStore(productDto.getAmountInStore());
    productRepository.save(product);
  }

  public void deleteProductById(Long id){
    productRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Client with provided id does not exist"));
    productRepository.deleteById(id);
  }


}
