package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.ClientDto;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
  ClientDto toDto(Client client);
  Client toEntity(ClientDto clientDto);
}
