package com.github.ferstl.junt5springboot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public ApplicationRunner junitRunner() {
    return new JunitApplicationRunner();
  }
}
