package com.laytin.SpringRESTApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "triporder")
public class TripOrder implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne()
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    @JsonIgnore
    private Trip trip;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer passenger;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private TripOrderStatus status;
    @Column(name = "sits")
    private int sits;

    public TripOrder() {
    }

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
