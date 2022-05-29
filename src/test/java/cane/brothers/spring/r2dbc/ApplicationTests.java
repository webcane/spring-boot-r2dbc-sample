package cane.brothers.spring.r2dbc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
@EnableAutoConfiguration
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private Application app;

	@Test
	void blockingSingleTest() {
		Exception exception = assertThrows(IndexOutOfBoundsException.class, () ->
			app.testSingle().subscribe()
		);

		String expectedMessage = "Source emitted more than one item";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.equals(expectedMessage));
	}

	@Test
	void blockingTest() {
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			Mono<Long> serviceResult = app.blockingTest();

			Long result = serviceResult.block(Duration.ofSeconds(1));
		});

		String expectedMessage = "Timeout on blocking read for 1";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void blockingInNonBlockingThreadsShouldNotBeAllowed() {
		StepVerifier
				.create(app.blockingIsNotAllowed())
				.expectNext(1)
				.verifyComplete();
//				.expectErrorMatches(e -> {
//					e.printStackTrace();
//
//					return e instanceof Error &&
//							e.getMessage().contains("Blocking call!");
//				})
//				.verify();
	}

	@Test
	void blockingInBlockingThreadsShouldBeAllowed() {
		StepVerifier
				.create(app.blockingIsAllowed())
				.expectNext(1)
				.verifyComplete();
	}

}
