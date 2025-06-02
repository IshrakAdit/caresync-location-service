package com.caresync.service.location;

import org.springframework.boot.SpringApplication;

public class TestLocationApplication {

	public static void main(String[] args) {
		SpringApplication.from(LocationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
