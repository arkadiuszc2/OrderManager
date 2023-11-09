package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateClientDto;
import com.mwo.OrderManager.entities.ViewClientDto;
import com.mwo.OrderManager.services.ClientService;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
  private final ClientService clientService;

  @PostMapping
  public ViewClientDto createClient(@RequestBody CreateClientDto createClientDto){
    return clientService.createClient(createClientDto);
  }
  @GetMapping("{id}")
  public ViewClientDto getClientById(@PathVariable Long id){
    ViewClientDto viewClientDto;
    try{
      viewClientDto = clientService.getClientById(id);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Client with provided id does not exist");
    }
    return viewClientDto;
  }
  @GetMapping
  public List<ViewClientDto> getAllClients(){
    return clientService.getAllClients();
  }
  @PutMapping("/{id}")
  public void updateClientById(@PathVariable Long id, @RequestBody ViewClientDto viewClientDto){
    try{
      clientService.updateClientById(id, viewClientDto);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Client with provided id does not exist");
    } catch (IllegalArgumentException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Provided id and client_id are not equal");
    }
  }
  @DeleteMapping("/{id}")
  public void deleteClientById(@PathVariable Long id){
    try{
      clientService.deleteClientById(id);
    } catch(NoSuchElementException e){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Client with provided id does not exist");
    }
  }
}
