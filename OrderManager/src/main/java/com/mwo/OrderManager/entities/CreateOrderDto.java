package com.mwo.OrderManager.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
  private Long id;
  private Long client_id;
  private List<Long> productsIds;

}
