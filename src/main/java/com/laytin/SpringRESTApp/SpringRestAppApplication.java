package com.laytin.SpringRESTApp;

import com.laytin.SpringRESTApp.dto.CustomerDTO;
import com.laytin.SpringRESTApp.dto.TripDTO;
import com.laytin.SpringRESTApp.dto.TripOrderDTO;
import com.laytin.SpringRESTApp.models.Customer;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

		modelMapper.addMappings(new PropertyMap<TripDTO, Trip>() {
			@Override
			protected void configure(){
				// Should fix it
				//skip(destination.getId());
				map(source.getCar(), destination.getCar());
				map(source.getTm(),destination.getTm());
			}
		});
		modelMapper.addMappings(new PropertyMap<TripOrderDTO, TripOrder>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		modelMapper.addMappings(new PropertyMap<TripOrder, TripOrderDTO>() {
			@Override
			protected void configure() {
				skip(destination.getTrip());
				//map(destination.getTrip().getId(),source.getTrip().getId());
			}
		});
		modelMapper.createTypeMap(Trip.class,TripDTO.class,"for_bitches").addMappings(new PropertyMap<Trip, TripDTO>() {
			@Override
			protected void configure() {
				skip(destination.getPassengers());
			}
		});
		modelMapper.createTypeMap(Trip.class,TripDTO.class,"for_owner").addMappings(new PropertyMap<Trip, TripDTO>() {
			@Override
			protected void configure() {
				map(source.getPassengers(),destination.getPassengers());
			}
		});
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE)
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
      			.setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
		return modelMapper;
	}
}
