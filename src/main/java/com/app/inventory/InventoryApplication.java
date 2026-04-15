package com.app.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryApplication {

  static void main(String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }

}
