package com.example.authcommon.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.authcommon.repository")
@EntityScan(basePackages = "com.example.authcommon.entity")
public class AppConfig {
}
