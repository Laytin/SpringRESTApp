package com.laytin.SpringRESTApp.utils;

import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CarValidator implements Validator {
    private final CarService carService;
    @Autowired
    public CarValidator(CarService carService) {
        this.carService = carService;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Car.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    }
}
