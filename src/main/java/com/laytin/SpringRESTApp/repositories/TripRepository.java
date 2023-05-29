package com.laytin.SpringRESTApp.repositories;

import com.laytin.SpringRESTApp.models.Trip;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {
    List<Trip> findByCustomerId(Integer id, PageRequest pageable);
    List<Trip> findByPassengersPassengerIdAndTmLessThan(Integer id, Timestamp tm,PageRequest pageable);
    List<Trip> findByPassengersPassengerIdAndTmGreaterThan(Integer id, Timestamp tm, PageRequest pageable);
}
