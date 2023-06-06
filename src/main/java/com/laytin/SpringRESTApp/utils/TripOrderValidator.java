package com.laytin.SpringRESTApp.utils;

import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrder;
import com.laytin.SpringRESTApp.repositories.TripOrderRepository;
import com.laytin.SpringRESTApp.repositories.TripRepository;
import com.laytin.SpringRESTApp.security.CustomerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class TripOrderValidator implements Validator {
    private final TripRepository tripRepository;
    @Autowired
    public TripOrderValidator(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TripOrder.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TripOrder o = (TripOrder) target;
        Optional<Trip> t = tripRepository.findById(o.getTrip().getId());
        if(t.isEmpty())
            errors.rejectValue("trip", "", "Trip does not exist");
        if(t.get().getFree_sits()<o.getSits())
            errors.rejectValue("sits", "", "No more sits!");
        if(t.get().getCustomer().getId()==((CustomerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCustomer().getId())
            errors.rejectValue("customer","","Owner cant be a passenger");
    }
}
