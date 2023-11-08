package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;


@Mapper
public interface OrderMapper {
  OrderDto toDto(Product product);
  Order toEntity(OrderDto productInOrderDto);
}
