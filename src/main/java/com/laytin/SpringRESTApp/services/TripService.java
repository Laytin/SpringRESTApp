package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.repositories.CarRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final CarRepository carRepository;
    @Autowired
    public TripService(TripRepository tripRepository, CarRepository carRepository) {
        this.tripRepository = tripRepository;
        this.carRepository = carRepository;
    }
    public void register(Trip t, CustomerDetails cd){
        t.setCustomer(cd.getCustomer());
        tripRepository.save(t);
    }
}
