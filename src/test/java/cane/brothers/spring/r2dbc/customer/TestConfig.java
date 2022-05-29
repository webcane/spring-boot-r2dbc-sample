package cane.brothers.spring.r2dbc.customer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

public class TestConfig {

  @Configuration
  @EnableR2dbcRepositories(basePackages = "cane.brothers.spring.r2dbc")
  @ConditionalOnProperty(value = "r2dbc.repositories.enabled", matchIfMissing = true, havingValue = "true")
  static class R2dbcConfig {
  }
}
