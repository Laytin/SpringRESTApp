package com.laytin.SpringRESTApp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laytin.SpringRESTApp.models.Car;
import com.laytin.SpringRESTApp.models.City;
import com.laytin.SpringRESTApp.models.Customer;

import javax.persistence.*;
import java.sql.Timestamp;

public class TripDTO {
    private int id;
    private CarDTO car;

    private int free_sits;

    private City place_from;

    private City place_to;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp tm;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTm() {
        return tm;
    }

    public void setTm(Timestamp tm) {
        this.tm = tm;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
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
