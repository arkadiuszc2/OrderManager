package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateClientDto;
import com.mwo.OrderManager.entities.CreateOrderDto;
import com.mwo.OrderManager.entities.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderCreateMapper {
  CreateOrderDto toDto(Order order);
  Order toEntity(CreateOrderDto createOrderDto);
}
