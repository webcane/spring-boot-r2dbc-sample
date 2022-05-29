package cane.brothers.spring.r2dbc.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


  private final CustomerRepository customerRepository;

  @Override
  public Flux<Customer> getAll() {
    return customerRepository.findAll();
  }

  @Override
  public Flux<Customer> getByLastName(String lastName) {
    return customerRepository.findByLastName(lastName);
  }

  @Override
  public Mono<Customer> save(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Mono<Customer> get(Long id) {
    return customerRepository.findById(id);
  }

  @Override
  public Mono<Customer> update(Long id, Customer customer) {
    return this.customerRepository.findById(id)
        .map(p -> {
          p.setLastName(customer.getLastName());
          p.setFirstName(customer.getFirstName());
          return p;
        })
        .flatMap(p -> this.customerRepository.save(p));
  }

  @Override
  public Mono<Void> delete(Long id) {
    return this.customerRepository.deleteById(id);
  }
}
