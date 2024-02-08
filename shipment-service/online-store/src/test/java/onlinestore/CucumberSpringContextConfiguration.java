package onlinestore;

import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = OnlineStoreApplication.class)
public class CucumberSpringContextConfiguration {
}
