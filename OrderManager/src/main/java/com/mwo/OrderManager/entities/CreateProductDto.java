package com.mwo.OrderManager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
  private String name;
  private double price;
  private Long amountInStore;
}
