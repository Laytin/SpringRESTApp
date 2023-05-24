package com.laytin.SpringRESTApp.repositories;

import com.laytin.SpringRESTApp.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer> {
}
