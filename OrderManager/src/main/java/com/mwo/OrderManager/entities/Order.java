package com.mwo.OrderManager.entities;

import com.mwo.OrderManager.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;
  @ManyToMany
  @JoinTable(
  name = "orders_products",
  joinColumns = @JoinColumn(name = "order_id"),
  inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;
  @Enumerated(EnumType.STRING)
  private Status status;

}
