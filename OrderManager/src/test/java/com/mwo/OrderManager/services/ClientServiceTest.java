package com.mwo.OrderManager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mwo.OrderManager.entities.Client;
import com.mwo.OrderManager.entities.CreateClientDto;
import com.mwo.OrderManager.entities.ViewClientDto;
import com.mwo.OrderManager.mappings.ClientCreateMapper;
import com.mwo.OrderManager.mappings.ClientCreateMapperImpl;
import com.mwo.OrderManager.mappings.ClientMapper;
import com.mwo.OrderManager.mappings.ClientMapperImpl;
import com.mwo.OrderManager.repositories.ClientRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ClientServiceTest {
  private final ClientCreateMapper clientCreateMapper = new ClientCreateMapperImpl();
  private final ClientMapper clientMapper = new ClientMapperImpl();
  private ClientRepository clientRepository = mock(ClientRepository.class);

  private ClientService clientService = new ClientService(clientRepository,clientMapper,clientCreateMapper);

  @AfterEach
  void cleanup() {
    Mockito.reset(clientRepository);
  }

  @Test
  void createClient_forValidCreateClientDto_shouldReturnCorrectClient() {
    // Arrange
    String name = "Marek";
    String surname = "Kowalski";
    String email = "marek.kowalski@gmail.com";
    Long clientId = 1L;
    Client client = Client.builder()
        .id(clientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();
    ViewClientDto expectedViewClientDto = ViewClientDto.builder()
        .id(clientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();
    CreateClientDto createClientDto = clientCreateMapper.toDto(client);
    when(clientRepository.save(any())).thenReturn(client);
    // Act
    ViewClientDto result = clientService.createClient(createClientDto);

    // Assert
    assertEquals(expectedViewClientDto, result);
  }

  @Test
  void getClientById_forExistingClient_shouldReturnCorrectClient() {
    // Arrange
    String name = "Marek";
    String surname = "Kowalski";
    String email = "marek.kowalski@gmail.com";
    Long clientId = 1L;
    Client existingClient = Client.builder()
        .id(clientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));

    ViewClientDto expectedViewClientDto = ViewClientDto.builder()
        .id(clientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();


    // Act
    ViewClientDto result = clientService.getClientById(clientId);

    // Assert
    assertEquals(expectedViewClientDto, result);
  }

  @Test
  void getClientById_forNonExistingClient_shouldThrowNoSuchElementException() {
    // Arrange
    Long nonExistingClientId = 2L;

    when(clientRepository.findById(nonExistingClientId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> clientService.getClientById(nonExistingClientId));
  }
  @Test
  void updateClientById_forValidInput_shouldUpdateClient() {
    // Arrange
    Long clientId = 1L;
    String updatedName = "Marek";
    String updatedSurname = "Kowalski";
    String updatedEmail = "marek.kowalski@gmail.com";

    ViewClientDto updatedClientDto = ViewClientDto.builder()
        .id(clientId)
        .name(updatedName)
        .surname(updatedSurname)
        .email(updatedEmail)
        .build();

    Client existingClient = Client.builder()
        .id(clientId)
        .name("OldName")
        .surname("OldSurname")
        .email("old.email@example.com")
        .build();

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
    when(clientRepository.save(existingClient)).thenReturn(existingClient);
    Client expected = clientMapper.toEntity(updatedClientDto);
    // Act
    clientService.updateClientById(clientId, updatedClientDto);

    // Assert
    assertEquals(expected, existingClient);
  }

  @Test
  void updateClientById_forMismatchedId_shouldThrowIllegalArgumentException() {
    // Arrange
    Long clientId = 1L;
    Long mismatchedId = 2L;
    String updatedName = "Marek";
    String updatedSurname = "Kowalski";
    String updatedEmail = "marek.kowalski@gmail.com";

    ViewClientDto updatedClientDto = ViewClientDto.builder()
        .id(mismatchedId)
        .name(updatedName)
        .surname(updatedSurname)
        .email(updatedEmail)
        .build();

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> clientService.updateClientById(clientId, updatedClientDto));
  }

  @Test
  void updateClientById_forNonExistingClient_shouldThrowNoSuchElementException() {
    // Arrange
    Long nonExistingClientId = 2L;
    String updatedName = "Marek";
    String updatedSurname = "Kowalski";
    String updatedEmail = "marek.kowalski@gmail.com";

    ViewClientDto updatedClientDto = ViewClientDto.builder()
        .id(nonExistingClientId)
        .name(updatedName)
        .surname(updatedSurname)
        .email(updatedEmail)
        .build();

    when(clientRepository.findById(nonExistingClientId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> clientService.updateClientById(nonExistingClientId, updatedClientDto));
  }
  @Test
  void deleteClientById_forExistingClient_shouldDeleteCorrectClient() {
    // Arrange
    Long clientId = 2L;
    String name = "Marek";
    String surname = "Kowalski";
    String email = "marek.kowalski@gmail.com";

    Client client = Client.builder()
        .id(clientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();

    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    // Act
    clientService.deleteClientById(clientId);
    //Assert
    verify(clientRepository).deleteById(clientId);
  }

  @Test
  void deleteClientById_forNonExistingClient_shouldThrowNoSuchElementException() {
    // Arrange
    Long nonExistingClientId = 2L;
    String name = "Marek";
    String surname = "Kowalski";
    String email = "marek.kowalski@gmail.com";

    ViewClientDto updatedClientDto = ViewClientDto.builder()
        .id(nonExistingClientId)
        .name(name)
        .surname(surname)
        .email(email)
        .build();

    when(clientRepository.findById(nonExistingClientId)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(NoSuchElementException.class, () -> clientService.updateClientById(nonExistingClientId, updatedClientDto));
  }

}