package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Product;
import com.mwo.OrderManager.entities.ViewProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
  ViewProductDto toDto(Product product);
  Product toEntity(ViewProductDto viewProductDto);
}
