package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.CarDTO;
import com.laytin.SpringRESTApp.dto.CarResponce;
import com.laytin.SpringRESTApp.dto.CustomerDTO;
import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.models.Customer;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;
    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }
    @GetMapping()
    public List<CarResponce> getCars(Authentication auth){
        List<Car> cars = carService.getCustomerCars((CustomerDetails)auth.getPrincipal());
        if(cars==null || cars.isEmpty())
            return new ArrayList<>();
        List<CarResponce> result = new ArrayList<>();
        cars.stream().forEach(c -> result.add(modelMapper.map(c, CarResponce.class)));
        return result;
    }
    @GetMapping("/{id}")
    public CarResponce getCar(@PathVariable("id") int id, Authentication auth){
        Car car = carService.getCar(id, (CustomerDetails) auth.getPrincipal());
        if(car==null)
            throw new RuntimeException();
        return modelMapper.map(car,CarResponce.class);
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid CarDTO carDTO, BindingResult result, Authentication auth){
        if(result.hasErrors())
            throw new RuntimeException();

        Car c = modelMapper.map(carDTO, Car.class);
        carService.register(c,(CustomerDetails)auth.getPrincipal());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id, @RequestBody @Valid CarDTO carDTO, BindingResult result, Authentication auth) {
        if(result.hasErrors())
            throw new RuntimeException();

        Car c = modelMapper.map(carDTO, Car.class);

        if(!carService.edit(id,c,(CustomerDetails)auth.getPrincipal(),result))
            throw new RuntimeException();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
