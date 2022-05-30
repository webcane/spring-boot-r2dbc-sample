package cane.brothers.spring.r2dbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;


@SpringBootTest
class BlockHoundTests {

	@Autowired
	private Application app;

	@Test
	void blockingInNonBlockingThreadsShouldNotBeAllowedTest() {
		StepVerifier.create(app.blockingIsNotAllowed())
				.expectErrorMatches(e -> {
					e.printStackTrace();
					return e instanceof Error &&
							e.getMessage().contains("Blocking call!");
				})
				.verify();
	}

	@Test
	void blockingInBlockingThreadsShouldBeAllowedTest() {
		StepVerifier.create(app.blockingIsAllowed())
				.expectNext(1)
				.verifyComplete();
	}
}
