package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.models.*;
import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.utils.rabbitmq.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripOrderService {
    private final CustomerRepository customerRepository;
    private final TripRepository tripRepository;
    private final TripOrderRepository tripOrderRepository;
    private final RabbitMQSender rqm;
    @Autowired
    public TripOrderService(CustomerRepository customerRepository, TripRepository tripRepository, TripOrderRepository tripOrderRepository, RabbitMQSender rqm) {
        this.customerRepository = customerRepository;
        this.tripRepository = tripRepository;
        this.tripOrderRepository = tripOrderRepository;
        this.rqm = rqm;
    }

    public boolean join(TripOrder o, CustomerDetails principal) {
        Optional<Trip> t = tripRepository.findById(o.getTrip().getId());
        if(!t.isPresent() || t.get().getFree_sits()<o.getSits())
             return false;
        t.get().setFree_sits(t.get().getFree_sits()-o.getSits());
        o.setStatus(TripOrderStatus.WAITING_DECISION);
        o.setPassenger(principal.getCustomer());
        tripOrderRepository.save(o);
        tripRepository.save(t.get());
        rqm.sendObject(o, "r.order-create");
        return true;
    }
    public List<TripOrder> getOrders(int pagenum, String valueWhat, CustomerDetails principal) {
        switch (valueWhat){
            case "active":
                return tripOrderRepository.findByPassengerIdAndTripTmGreaterThan(principal.getCustomer().getId(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        PageRequest.of(pagenum-1,10, Sort.by(Sort.Direction.DESC,"trip.tm")));
            case "old":
                return tripOrderRepository.findByPassengerIdAndTripTmLessThan(principal.getCustomer().getId(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        PageRequest.of(pagenum-1,10,Sort.by(Sort.Direction.DESC,"trip.tm")));
            default:
                return new ArrayList<>();
        }

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
                to.get().setStatus(status);
                rqm.sendObject(to, "r.order-edit");
                return tripOrderRepository.save(to.get());
            case CANCELED:
                t.setFree_sits(t.getFree_sits()+to.get().getSits());
                to.get().setStatus(status);
                rqm.sendObject(to, "r.order-edit");
                tripRepository.save(t);
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
