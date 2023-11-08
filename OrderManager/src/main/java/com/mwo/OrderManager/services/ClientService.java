package com.mwo.OrderManager.services;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.ClientDto;
import com.mwo.OrderManager.mappings.ClientMapper;
import com.mwo.OrderManager.repositories.ClientRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.management.BadAttributeValueExpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ClientService {
  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;
  public ClientDto createClient(ClientDto clientDto){
    Client client = clientRepository.save(clientMapper.toEntity(clientDto));
    return clientMapper.toDto(client);    //changed here
  }

  public ClientDto getClientById(Long id){
    return clientRepository.findById(id).map(clientMapper::toDto).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Client with provided id does not exist"));
  }

  public void updateClientById(Long id, ClientDto clientDto){
    if (!Objects.equals(id, clientDto.getId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided id and client id are not equal");
    }
    Client client = clientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Client with provided id does not exist"));
    client.setEmail(clientDto.getEmail());
    client.setName(clientDto.getName());
    client.setSurname(clientDto.getSurname());
    clientRepository.save(client);
  }

  public void deleteClientById(Long id){
    clientRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,
        "Client with provided id does not exist"));
    clientRepository.deleteById(id);
  }

}
