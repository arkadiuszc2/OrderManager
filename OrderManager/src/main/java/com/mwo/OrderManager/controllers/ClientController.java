package com.mwo.OrderManager.controllers;

import com.mwo.OrderManager.entities.ClientDto;
import com.mwo.OrderManager.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
  private final ClientService clientService;

  @PostMapping
  public ClientDto createClient(@RequestBody ClientDto clientDto){
    return clientService.createClient(clientDto);
  }
  @GetMapping("{id}")
  public ClientDto getClientById(@PathVariable Long id){
    return clientService.getClientById(id);
  }
  @PutMapping("/{id}")
  public void updateClientById(@PathVariable Long id, @RequestBody ClientDto clientDto){
    clientService.updateClientById(id, clientDto);
  }
  @DeleteMapping("/{id}")
  public void deleteClientById(@PathVariable Long id){
    clientService.deleteClientById(id);
  }
}
