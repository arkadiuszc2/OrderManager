package com.mwo.OrderManager.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewProductDto {
  private Long id;
  private String name;
  private double price;
  private Long amountInStore;

}
