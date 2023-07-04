package com.example.farajaplatform;

import com.example.farajaplatform.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FarajaPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarajaPlatformApplication.class, args);
	}
	@Bean
	Person setPerson(){
		return new Person();
	}

}
