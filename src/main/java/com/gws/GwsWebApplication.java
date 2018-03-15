package com.gws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GwsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwsWebApplication.class, args);
	}
}
