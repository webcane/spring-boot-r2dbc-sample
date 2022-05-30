package cane.brothers.spring.r2dbc.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(TestConfig.class)
class CustomerRepositoryTest {

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  R2dbcEntityTemplate template;

  @BeforeEach
  public void setup() {
    this.template.delete(Customer.class).all().block(Duration.ofSeconds(5));
  }

  @Test
  public void testDatabaseClientExisted() {
    assertNotNull(template);
  }

  @Test
  public void testPostRepositoryExisted() {
    assertNotNull(customerRepo);
  }

  @Test
  void testFindAll() {
    Customer customer1 = Customer.builder().firstName("test1").lastName("test1").build();
    Customer customer2 = Customer.builder().firstName("test2").lastName("test2").build();

    template.insert(Customer.class)
        .using(customer1)
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    template.insert(Customer.class)
        .using(customer2)
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    StepVerifier.create(this.customerRepo.findAll())
        .expectNext(customer1, customer2)
        .expectComplete()
        .verify();
  }

  @Test
  void testFindByLastName() {
    Customer customer1 = Customer.builder().firstName("test1").lastName("test1").build();
    Customer customer2 = Customer.builder().firstName("test2").lastName("test2").build();

    template.insert(customer1)
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    template.insert(customer2)
        .as(StepVerifier::create)
        .expectNextCount(1)
        .verifyComplete();

    StepVerifier.create(this.customerRepo.findByLastName("test2"))
        .consumeNextWith(c -> {
          assertEquals("test2", c.getFirstName());
          assertEquals("test2", c.getLastName());
        })
        .verifyComplete();
  }
}