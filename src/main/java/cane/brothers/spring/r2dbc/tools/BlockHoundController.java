package cane.brothers.spring.r2dbc.tools;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/blockhound")
public class BlockHoundController {

  @GetMapping
  public Mono<Void> block() {
    Mono.just("")
        .map(a -> "123")
            .block();

    return Mono.empty();
  }
}
