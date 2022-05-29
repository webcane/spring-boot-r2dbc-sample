package cane.brothers.spring.r2dbc.tools;

import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.blockhound.BlockHound;

/**
 * Spring configuration to enable BlockHound scanner.
 */
@Profile("blockhound")
@Component
public class BlockHoundHandler {

  @PostConstruct
  void postConstruct(){
    BlockHound.install();
  }
}
