package com.cptkagan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cptkagan")
public class NotesappBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesappBackendApplication.class, args);
	}

}
