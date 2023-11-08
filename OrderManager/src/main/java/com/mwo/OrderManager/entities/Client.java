package com.mwo.OrderManager.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name="CLIENTS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Client {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String surname;
  private String email;
  @OneToMany(mappedBy = "client")
  private List<Order> orders;

}
