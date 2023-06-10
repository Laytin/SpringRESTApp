package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.dao.TripDAO;
import com.laytin.SpringRESTApp.dto.TripDTO;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.repositories.CarRepository;
import com.laytin.SpringRESTApp.repositories.CustomerRepository;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final TripOrderRepository tripOrderRepository;
    private final TripDAO tripDAO;
    @Autowired
    public TripService(TripRepository tripRepository, CarRepository carRepository, CustomerRepository customerRepository, TripOrderRepository tripOrderRepository, TripDAO tripDAO) {
        this.tripRepository = tripRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.tripOrderRepository = tripOrderRepository;
        this.tripDAO = tripDAO;
    }
    public void register(Trip t, CustomerDetails cd){
        t.setCustomer(cd.getCustomer());
        tripRepository.save(t);
    }

    public boolean edit(int id,Trip t, CustomerDetails principal) {
        Optional<Trip> edited = tripRepository.findById(id);
        if(edited.isEmpty() || edited.get().getCustomer().getId()!= principal.getCustomer().getId())
            return false;
        t.setCustomer(edited.get().getCustomer());
        t.setId(id);
        tripRepository.save(t);
        return true;
    }

    public List<Trip> getTrips(LocalDate tm, City placeFrom, City placeTo, int sits, int page) {
        return tripDAO.getTrips(tm,placeFrom,placeTo,sits, page);
    }
    public List<Trip> getOwnerTrips(int page, CustomerDetails cd){
        List<Trip> trips = tripRepository.findByCustomerId(cd.getCustomer().getId(), PageRequest.of(page-1,10, Sort.by(Sort.Direction.DESC,"tm")));
        return trips;
    }

    public List<TripOrder> getOrders(int pagenum, String valueWhat, CustomerDetails principal) {
        switch (valueWhat){
            case "active":
                return tripOrderRepository.findByPassengerIdAndTripTmGreaterThan(principal.getCustomer().getId(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        PageRequest.of(pagenum-1,10,Sort.by(Sort.Direction.DESC,"tm")));
            case "old":
                return tripOrderRepository.findByPassengerIdAndTripTmLessThan(principal.getCustomer().getId(),
                        Timestamp.valueOf(LocalDateTime.now()),
                        PageRequest.of(pagenum-1,10,Sort.by(Sort.Direction.DESC,"trip_tm")));
            default:
                return new ArrayList<>();
        }

    }
    public boolean delete(int id, CustomerDetails principal) {
        Optional<Trip> t = tripRepository.findById(id);
        if(t.isEmpty() || t.get().getCustomer()!=principal.getCustomer()){
            return false;
        }
        tripRepository.delete(t.get());
        return true;
    }

    public Trip getTripById(int id, CustomerDetails principal) {
        Optional<Trip> t = tripRepository.findById(id);
        if(t.isEmpty() || t.get().getCustomer().getId()!=principal.getCustomer().getId())
            return null;
        return t.get();
    }
}
