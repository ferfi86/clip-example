package com.example.clip;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClipApplication {
	
	@Bean
	public ModelMapper getModelMapper() {
		return  new ModelMapper();
	}

    public static void main(String[] args) { SpringApplication.run(ClipApplication.class, args);}
}
