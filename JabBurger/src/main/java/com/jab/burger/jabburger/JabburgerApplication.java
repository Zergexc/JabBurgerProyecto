package com.jab.burger.jabburger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jab.burger.jabburger"})
public class JabburgerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JabburgerApplication.class, args);
	}

}
