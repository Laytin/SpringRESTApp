package com.laytin.SpringRESTApp.services;

import com.laytin.SpringRESTApp.dao.TripDAO;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.repositories.CarRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final CarRepository carRepository;
    private final TripDAO tripDAO;
    @Autowired
    public TripService(TripRepository tripRepository, CarRepository carRepository, TripDAO tripDAO) {
        this.tripRepository = tripRepository;
        this.carRepository = carRepository;
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

    public List<Trip> getTrips(LocalDate tm, City placeFrom, City placeTo, int sits) {
        return tripDAO.getTrips(tm,placeFrom,placeTo,sits);
    }
}
