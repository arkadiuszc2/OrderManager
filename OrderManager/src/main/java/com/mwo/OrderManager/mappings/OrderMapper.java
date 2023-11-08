package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Order;
import com.mwo.OrderManager.entities.OrderDto;
import org.mapstruct.Mapper;


@Mapper
public interface OrderMapper {
  OrderDto toDto(Order order);
  Order toEntity(OrderDto orderDto);
}
