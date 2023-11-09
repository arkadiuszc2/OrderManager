package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateClientDto;
import com.mwo.OrderManager.entities.ViewClientDto;
import com.mwo.OrderManager.mappings.ClientCreateMapper;
import com.mwo.OrderManager.mappings.ClientMapper;
import com.mwo.OrderManager.repositories.ClientRepository;
import java.util.NoSuchElementException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;
  private ClientCreateMapper clientCreateMapper;
  public ViewClientDto createClient(CreateClientDto createClientDto){
    Client client = clientRepository.save(clientCreateMapper.toEntity(createClientDto));
    return clientMapper.toDto(client);
  }

  public ViewClientDto getClientById(Long id){
    return clientRepository.findById(id).map(clientMapper::toDto).orElseThrow(
        NoSuchElementException::new);
  }

  public void updateClientById(Long id, ViewClientDto viewClientDto){
    if (!Objects.equals(id, viewClientDto.getId())){
      throw new IllegalArgumentException();
    }
    Client client = clientRepository.findById(id).orElseThrow(NoSuchElementException::new);
    client.setEmail(viewClientDto.getEmail());
    client.setName(viewClientDto.getName());
    client.setSurname(viewClientDto.getSurname());
    clientRepository.save(client);
  }

  public void deleteClientById(Long id){
    clientRepository.findById(id).orElseThrow(NoSuchElementException::new);
    clientRepository.deleteById(id);
  }

}
