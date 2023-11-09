package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateClientDto;
import com.mwo.OrderManager.entities.ViewClientDto;
import org.mapstruct.Mapper;

@Mapper
public interface ClientCreateMapper {
  CreateClientDto toDto(Client client);
  Client toEntity(CreateClientDto createClientDto);
}
