package com.backend.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
public class WorkflowApplication {


	public static void main(String[] args) {
		SpringApplication.run(WorkflowApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

}
