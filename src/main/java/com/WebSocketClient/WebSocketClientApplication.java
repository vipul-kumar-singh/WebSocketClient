package com.WebSocketClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class WebSocketClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketClientApplication.class, args);
	}

}

