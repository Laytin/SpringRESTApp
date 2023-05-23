package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.dto.CarDTO;
import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepository;
    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void register(Car c) {
        carRepository.save(c);
    }

    public void edit(CarDTO carDTO) {

    }
}
