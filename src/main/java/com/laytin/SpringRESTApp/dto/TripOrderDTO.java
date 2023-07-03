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

    private TripDTO trip;

    private CustomerDTO passenger;

    private TripOrderStatus status;

    private int sits;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    public CustomerDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(CustomerDTO passenger) {
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

    @Override
    public String toString() {
        return "TripOrderDTO{" +
                "id=" + id +
                ", trip=" + trip +
                ", passenger=" + passenger +
                ", status=" + status +
                ", sits=" + sits +
                '}';
    }
}
