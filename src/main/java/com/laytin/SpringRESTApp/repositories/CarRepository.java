package com.laytin.SpringRESTApp.repositories;

import com.laytin.SpringRESTApp.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {
    List<Car> findByCustomerId(Integer id);
}
