package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.dto.TripDTO;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import com.laytin.SpringRESTApp.services.TripService;
import com.laytin.SpringRESTApp.utils.errors.DefaulErrorResponce;
import com.laytin.SpringRESTApp.utils.errors.DefaultErrorException;
import com.laytin.SpringRESTApp.utils.validators.TripValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.laytin.SpringRESTApp.utils.errors.ErrorBuilder.buildErrorMessageForClient;

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
            buildErrorMessageForClient(result);
        tripService.register(t,(CustomerDetails)auth.getPrincipal());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") int id,@RequestBody @Valid TripDTO tripDTO, BindingResult result, Authentication auth){
        Trip t = modelMapper.map(tripDTO, Trip.class);
        tripValidator.validate(t,result);
        if(result.hasErrors())
            buildErrorMessageForClient(result);
        if(!tripService.edit(id,t,(CustomerDetails)auth.getPrincipal()))
            buildErrorMessageForClient("Error due editing trip, try again");
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id, Authentication auth){
        if(!tripService.delete(id,(CustomerDetails)auth.getPrincipal()))
            buildErrorMessageForClient("Error due deleting trip. Are u rly owner of this trip?");
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/search")
    public List<TripDTO> findTrips(@RequestParam(value = "date",  required = true) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDate timing,
                               @RequestParam(value = "place_from", required = true) City place_from,
                               @RequestParam(value = "place_to", required = true) City place_to,
                               @RequestParam(value = "sits", required = true) int sits,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        List<TripDTO> trips = tripService.getTrips(timing,place_from,place_to,sits,page).
                stream().map(s-> modelMapper.map(s,TripDTO.class,"for_bitches")).collect(Collectors.toList());
        //cringe
        //trips.forEach(s->s.setPassengers(null));
        return trips;
    }
    @GetMapping("/my")
    public List<TripDTO> getMyTrips(@RequestParam(value = "page",  required = false, defaultValue = "1") int pagenum, Authentication auth){
        List<TripDTO> trips =tripService.getOwnerTrips(pagenum,(CustomerDetails) auth.getPrincipal()).
            stream().map(s-> modelMapper.map(s,TripDTO.class,"for_owner")).collect(Collectors.toList());
        return trips;
    }

    @GetMapping("/{id}")
    public TripDTO getId(@PathVariable("id") int id, Authentication auth){
        Trip t = tripService.getTripById(id,(CustomerDetails)auth.getPrincipal());
        if(t==null)
            buildErrorMessageForClient("Trip does not exist!");
        if(t.getCustomer().getId()==((CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCustomer().getId())
            return modelMapper.map(t,TripDTO.class,"for_owner");
        return modelMapper.map(t,TripDTO.class,"for_bitches");
    }
    @ExceptionHandler
    private ResponseEntity<DefaulErrorResponce> handleException(DefaultErrorException e) {
        DefaulErrorResponce response = new DefaulErrorResponce(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
