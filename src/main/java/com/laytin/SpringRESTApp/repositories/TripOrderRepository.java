package com.laytin.SpringRESTApp.repositories;

import com.laytin.SpringRESTApp.models.TripOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TripOrderRepository extends JpaRepository<TripOrder, Integer> {
    //@Query("SELECT u FROM TripOrder u WHERE u.passenger.id= ?1 AND u.trip.tm = ?2")
    List<TripOrder> findByPassengerIdAndTripTmGreaterThan(Integer id, Timestamp tm,PageRequest pr);
    List<TripOrder> findByPassengerIdAndTripTmLessThan(Integer id, Timestamp tm,PageRequest pr);
}
