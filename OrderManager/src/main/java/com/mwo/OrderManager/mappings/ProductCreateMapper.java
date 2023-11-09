package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.CreateProductDto;
import com.mwo.OrderManager.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductCreateMapper {
  CreateProductDto toDto(Product product);
  Product toEntity(CreateProductDto createProductDto);
}
