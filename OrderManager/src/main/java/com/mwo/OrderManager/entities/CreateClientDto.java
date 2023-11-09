package com.mwo.OrderManager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateClientDto {
  private String name;
  private String surname;
  private String email;
}
