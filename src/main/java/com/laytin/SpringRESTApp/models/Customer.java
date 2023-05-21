package com.laytin.SpringRESTApp.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;
    @Column(name = "username")
    private String fullname;
    @Column(name = "username")
    @Email
    private String email;
    @Column(name = "username")
    private String phone;
    @Column(name = "username")
    private String password;
    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Car> cars;
    @OneToMany(mappedBy = "customer")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Trip> owningtrips;

    @ManyToMany(mappedBy = "passenger")
    private List<Trip> mytrips;
    public Customer() {
    }

    public Customer(String username, String fullname, String email, String phone, String password) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
