package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
  ProductDto toDto(Product product);
  Product toEntity(ProductDto productDto);
}
