package com.mwo.OrderManager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private Long amountInStore;
  @ManyToMany(mappedBy = "products")
  private List<Order> order;

}
