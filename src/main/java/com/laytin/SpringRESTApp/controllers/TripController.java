package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.TripDTO;
import com.laytin.SpringRESTApp.dto.TripResponce;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.TripService;
import com.laytin.SpringRESTApp.utils.TripValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id,@RequestBody @Valid TripDTO tripDTO, BindingResult result, Authentication auth){
        Trip t = modelMapper.map(tripDTO, Trip.class);
        tripValidator.validate(t,result);
        if(result.hasErrors())
            throw new RuntimeException();
        if(!tripService.edit(id,t,(CustomerDetails)auth.getPrincipal()))
            throw new RuntimeException();
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Authentication auth){
        if(!tripService.delete(id,(CustomerDetails)auth.getPrincipal()))
            throw new RuntimeException();
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/search")
    public List<TripResponce> findTrips(@RequestParam(value = "date",  required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDate timing,
                               @RequestParam(value = "place_from", required = true) City place_from,
                               @RequestParam(value = "place_to", required = true) City place_to,
                               @RequestParam(value = "sits", required = true) int sits,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        List<TripResponce> trips = tripService.getTrips(timing,place_from,place_to,sits,page).
                stream().map(s-> modelMapper.map(s,TripResponce.class)).collect(Collectors.toList());
        if(trips==null || trips.isEmpty())
            return new ArrayList<>();
        return trips;
    }
    @GetMapping("/my")
    public List<TripResponce> getMyTrips(@RequestParam(value = "page",  required = false, defaultValue = "1") int pagenum, Authentication auth){
        List<TripResponce> trips  =tripService.getOwnerTrips(pagenum,(CustomerDetails) auth.getPrincipal()).
            stream().map(s-> modelMapper.map(s,TripResponce.class)).collect(Collectors.toList());;
        if (trips==null || trips.isEmpty())
            return new ArrayList<>();
        return trips;
    }
    @GetMapping("/history")
    public List<TripOrder> getJoinedTrips(@RequestParam(value = "page",  required = false, defaultValue = "1") int pagenum,
                                          @RequestParam(value = "type",  required = true) String type, Authentication auth){
        return tripService.getOrders(pagenum,type,(CustomerDetails)auth.getPrincipal());
    }
    @PatchMapping("/{id}/join")
    public ResponseEntity<HttpStatus> join(@PathVariable("id") int id, Authentication auth){
        tripService.joinInTrip(id,(CustomerDetails) auth.getPrincipal());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}/leave")
    public ResponseEntity<HttpStatus> leave(@PathVariable("id") int id, Authentication auth){
        tripService.leaveFromTrip(id,(CustomerDetails) auth.getPrincipal());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
