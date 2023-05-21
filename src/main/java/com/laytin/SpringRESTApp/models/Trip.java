package com.laytin.SpringRESTApp.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Trip")
public class Trip {
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id", referencedColumnName="id")
    private Car car;
    @ManyToOne()
    @JoinColumn(name="customer_id", referencedColumnName="id")
    private Customer customer;
    @Column(name = "free_sits")
    private int free_sits;
    @Column(name = "place_from")
    @Enumerated(value = EnumType.STRING)
    private City place_from;
    @Column(name = "place_to")
    @Enumerated(value = EnumType.STRING)
    private City place_to;
    @Column(name = "time_out")
    private Timestamp tm;
    @ManyToMany
    @JoinTable(name = "trip_leg",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private List<Customer> passenger;
    public Trip() {
    }

    public Trip(Car car, Customer customer, int free_sits, City place_from, City place_to, Timestamp tm) {
        this.car = car;
        this.customer = customer;
        this.free_sits = free_sits;
        this.place_from = place_from;
        this.place_to = place_to;
        this.tm = tm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Timestamp getTm() {
        return tm;
    }

    public void setTm(Timestamp tm) {
        this.tm = tm;
    }
}