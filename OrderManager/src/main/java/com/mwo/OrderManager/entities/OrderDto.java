package com.mwo.OrderManager.entities;

import com.mwo.OrderManager.enums.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
  private Long id;
  private Client client;
  private List<Product> products;
  private Status status;
}
