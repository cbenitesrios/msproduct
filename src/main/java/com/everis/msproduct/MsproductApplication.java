package com.everis.msproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication 
@EnableWebFlux
public class MsproductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsproductApplication.class, args);
	}

}
