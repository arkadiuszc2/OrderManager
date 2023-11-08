package com.mwo.OrderManager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PRODUCTS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private double price;
  private boolean isAvailable;
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

}
