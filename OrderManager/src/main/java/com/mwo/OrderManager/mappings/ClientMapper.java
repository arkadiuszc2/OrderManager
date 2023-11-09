package com.mwo.OrderManager.mappings;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.ViewClientDto;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
  ViewClientDto toDto(Client client);
  Client toEntity(ViewClientDto viewClientDto);
}
