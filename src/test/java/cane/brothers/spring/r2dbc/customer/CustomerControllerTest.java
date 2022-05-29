package cane.brothers.spring.r2dbc.customer;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@WebFluxTest(controllers = CustomerController.class)
class CustomerControllerTest {

  @MockBean
  CustomerService customerService;

  @Autowired
  private WebTestClient webClient;

  @Test
  void all() {
    webClient.get()
        .uri("/customers")
        .exchange()
        .expectStatus().isOk();
  }

  @Test
  void getByLastName() {
    Customer customer = Customer.builder()
        .id(1L).firstName("Test").lastName("Bauer").build();

    Mockito.when(customerService.getByLastName(anyString()))
        .thenReturn(Flux.just(customer));

    webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/customers")
            .queryParam("lastName", "Bauer")
            .build())
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Customer.class).hasSize(1);
        //.expectBody()
        //.jsonPath("$.lastName").isEqualTo("Bauer");
//        .consumeWith(response ->
//            Assertions.assertThat(response.getResponseBody()).isNotNull());
  }

  @Test
  void create() {
  }

  @Test
  void get() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }
}