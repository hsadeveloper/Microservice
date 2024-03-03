package store.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "IRCTC API Definition", version = "2.0", description = "Train Ticket Booking Application"))
@SpringBootApplication
public class StoreserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreserviceApplication.class, args);
	}

}
