package userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class MongoConfig {
  
  @Autowired
  private MongoTemplate mongoTemplate;
  
  @PostConstruct
  public void createDatabase() {
    mongoTemplate.getDb().createCollection("hasancollection");
  }
}