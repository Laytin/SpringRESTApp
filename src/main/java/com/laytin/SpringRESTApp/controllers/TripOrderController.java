package com.laytin.SpringRESTApp.controllers;

import com.laytin.SpringRESTApp.services.TripOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TripOrderController {
    private final TripOrderService tripOrderService;

    @Autowired
    public TripOrderController(TripOrderService tripOrderService) {
        this.tripOrderService = tripOrderService;
    }
}
