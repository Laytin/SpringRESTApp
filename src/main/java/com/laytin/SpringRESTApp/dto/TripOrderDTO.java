package com.laytin.SpringRESTApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.laytin.SpringRESTApp.models.Customer;
import com.laytin.SpringRESTApp.models.Trip;
import com.laytin.SpringRESTApp.models.TripOrderStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

public class TripOrderDTO {
    private int id;

    private Trip trip;

    private Customer passenger;

    private TripOrderStatus status;

    private int sits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Customer getPassenger() {
        return passenger;
    }

    public void setPassenger(Customer passenger) {
        this.passenger = passenger;
    }

    public TripOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TripOrderStatus status) {
        this.status = status;
    }

    public int getSits() {
        return sits;
    }

    public void setSits(int sits) {
        this.sits = sits;
    }
}
