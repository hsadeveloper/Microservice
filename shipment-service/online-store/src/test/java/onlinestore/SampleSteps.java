package onlinestore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import onlinestore.service.ProductService;

public class SampleSteps {
  @Autowired
  private ProductService productService;

  private String apiUrl;
  private ResponseEntity<String> response;

  @Given("the API is running at http:\\/\\/localhost:{int}")
  public void the_api_is_running_at_http_localhost(Integer port) {
    this.apiUrl = apiUrl;
  }

  @When("a GET request is made to the endpoint \\/product\\/greet")
  public void aGetRequestIsMadeToTheEndpoint() throws URISyntaxException {
    String baseUrl = "http://localhost:8080";
    String path = "/product/api/greet";
    URI uri = new URI(baseUrl + path);
    RestTemplate restTemplate = new RestTemplate();
    response = restTemplate.getForEntity(uri, String.class);
  }

  @Then("the response should be {string}")
  public void theResponseShouldBe(String expectedResponse) {
    assertEquals(expectedResponse, response.getBody());
  }
}
