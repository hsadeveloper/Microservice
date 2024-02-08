package onlinestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @OpenAPIDefinition(info = @Info(title = "IRCTC API Definition", version = "2.0",
// description = "Train Ticket Booking Application"))
public class OnlineStoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(OnlineStoreApplication.class, args);
  }

}
