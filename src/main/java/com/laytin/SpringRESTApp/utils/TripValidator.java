package com.laytin.SpringRESTApp.utils;

import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TripValidator implements Validator {
    private final CarService carService;
    @Autowired
    public TripValidator(CarService carService) {
        this.carService = carService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Trip.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trip t = (Trip) target;
        Car c = carService.getCar(t.getCar().getId(),(CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(c==null){
         errors.rejectValue("car", "", "It isnt ur car!");
        }
    }
}
