package com.mwo.OrderManager.entities;

import com.mwo.OrderManager.enums.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateOrderDto {
  private Long client_id;
  private List<Long> productsIds;
  private Status status;
}
