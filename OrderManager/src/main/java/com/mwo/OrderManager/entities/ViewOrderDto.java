package com.mwo.OrderManager.entities;

import com.mwo.OrderManager.enums.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOrderDto {
  private Long id;
  private Long client_id;
  private List<Long> productsIds;
  private Status status;

}
