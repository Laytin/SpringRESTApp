package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import org.springframework.stereotype.Service;

@Service
public class TripOrderService {
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;
    private final TripOrderRepository tripOrderRepositor;

    public TripOrderService(CustomerRepository customerRepository, TripRepository tripRepository, TripOrderRepository tripOrderRepositor) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.tripOrderRepositor = tripOrderRepositor;
    }
}
