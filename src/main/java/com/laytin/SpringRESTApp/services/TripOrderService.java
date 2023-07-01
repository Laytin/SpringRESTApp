package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.models.TripOrderStatus;
import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class TripOrderService {
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;
    private final TripOrderRepository tripOrderRepository;

    public TripOrderService(CustomerRepository customerRepository, TripRepository tripRepository, TripOrderRepository tripOrderRepository) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.tripOrderRepository = tripOrderRepository;
    }

    public TripOrder join(TripOrder o, CustomerDetails principal) {
        o.setStatus(TripOrderStatus.WAITING_DECISION);
        o.setPassenger(principal.getCustomer());
        //t.setFree_sits(t.getFree_sits()-o.getSits());
        TripOrder res = tripOrderRepository.save(o);
        return res;
    }

    public TripOrder acceptOrDecline(int id, TripOrderStatus status, CustomerDetails principal, BindingResult result) {
        Optional<TripOrder> to = tripOrderRepository.findById(id);
        if(to.isEmpty())
            return null;

        Trip t = to.get().getTrip();
        if(t.getCustomer().getId()!=principal.getCustomer().getId()){
            result.rejectValue("id", "", "You are not an owner");
            return null;
        }
        switch (status){
            case ACCEPTED:
                if(to.get().getSits()>t.getFree_sits()){
                    to.get().setStatus(TripOrderStatus.CANCELED);
                    result.rejectValue("sits", "", "You have less free sits that in order! Сanceled forcibly");
                    tripOrderRepository.save(to.get());
                    break;
                }
                to.get().setStatus(status);
                t.setFree_sits(t.getFree_sits()-to.get().getSits());
                tripRepository.save(t);
                return tripOrderRepository.save(to.get());
            case CANCELED:
                to.get().setStatus(status);
                return tripOrderRepository.save(to.get());
        }
        return null;
    }

    public boolean delete(int id, CustomerDetails auth) {
        Optional<TripOrder> t = tripOrderRepository.findById(id);
        if(t.isEmpty() || t.get().getPassenger().getId()!=auth.getCustomer().getId())
            return false;
        tripOrderRepository.delete(t.get());
        return true;
    }
}
