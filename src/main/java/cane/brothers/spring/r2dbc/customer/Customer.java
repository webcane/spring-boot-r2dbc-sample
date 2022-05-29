package cane.brothers.spring.r2dbc.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  @Id
  private Long id;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;
}
