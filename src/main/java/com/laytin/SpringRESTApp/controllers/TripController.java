package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.TripDTO;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.TripService;
import com.laytin.SpringRESTApp.utils.TripValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;
    private final ModelMapper modelMapper;
    private final TripValidator tripValidator;
    @Autowired
    public TripController(TripService tripService, ModelMapper modelMapper, TripValidator tripValidator) {
        this.tripService = tripService;
        this.modelMapper = modelMapper;
        this.tripValidator = tripValidator;
    }
    @PostMapping()
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid TripDTO tripDTO, BindingResult result, Authentication auth){
        Trip t = modelMapper.map(tripDTO, Trip.class);
        tripValidator.validate(t,result);
        if(result.hasErrors())
            throw new RuntimeException();
        tripService.register(t,(CustomerDetails)auth.getPrincipal());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
