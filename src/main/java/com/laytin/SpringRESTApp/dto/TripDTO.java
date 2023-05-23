package com.laytin.SpringRESTApp.dto;

import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Customer;

import javax.persistence.*;

public class TripDTO {
    private Car car;

    private Customer customer;

    private int free_sits;

    private City place_from;

    private City place_to;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getFree_sits() {
        return free_sits;
    }

    public void setFree_sits(int free_sits) {
        this.free_sits = free_sits;
    }

    public City getPlace_from() {
        return place_from;
    }

    public void setPlace_from(City place_from) {
        this.place_from = place_from;
    }

    public City getPlace_to() {
        return place_to;
    }

    public void setPlace_to(City place_to) {
        this.place_to = place_to;
    }
}
