package com.laytin.SpringRESTApp;

import com.laytin.SpringRESTApp.dto.CustomerDTO;
import com.laytin.SpringRESTApp.models.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class SpringRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestAppApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<CustomerDTO, Customer>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		return modelMapper;
	}
}
