package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.models.TripOrderStatus;
import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void join(TripOrder o, CustomerDetails principal) {
        o.setStatus(TripOrderStatus.WAITING_DECISION);
        o.setPassenger(principal.getCustomer());
        Trip t = tripRepository.getById(o.getTrip().getId());
        t.setFree_sits(t.getFree_sits()-o.getSits());
        tripOrderRepositor.save(o);
        tripRepository.save(t);
    }
}
