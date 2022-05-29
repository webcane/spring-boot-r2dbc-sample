package cane.brothers.spring.r2dbc;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  public Mono<Integer> testSingle() {
    return Flux.range(0,5)
        .single()
        .subscribeOn(Schedulers.parallel());
  }

  public Mono<Long> blockingTest() {
    return Mono.delay(Duration.ofSeconds(1))
        .doOnNext(it -> {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public Mono<Integer> blockingIsAllowed() {
    return getBlockingMono()
        .subscribeOn(Schedulers.boundedElastic());
  }

  public Mono<Integer> blockingIsNotAllowed() {
    return getBlockingMono()
        .subscribeOn(Schedulers.parallel());
  }

  private Mono<Integer> getBlockingMono() {
    return Mono.just(1)
        .doOnNext(i -> block());
  }

  private void block() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      //Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }
}
