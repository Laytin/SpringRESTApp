package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.repositories.CarRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void register(Car c, CustomerDetails cd) {
        c.setCustomer(cd.getCustomer());
        carRepository.save(c);
    }

    public boolean edit(int id, Car car, CustomerDetails cd, BindingResult result) {
        Optional<Car> c = carRepository.findById(id);
        if(!c.isPresent() || c.get().getCustomer().getId()!=cd.getCustomer().getId()){
            return false;
        }
        car.setId(id);
        car.setCustomer(c.get().getCustomer());
        carRepository.save(car);
        return true;
    }
}
