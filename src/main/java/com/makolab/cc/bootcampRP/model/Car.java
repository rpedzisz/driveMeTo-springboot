package com.makolab.cc.bootcampRP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Car model class
 */
@Entity
@Table(name = "car")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    private String brand;

    private String model;

    private String currentLocalization;

    @OneToOne
    @JoinColumn(name = "driver_driver_id")
    @JsonIgnore
    private Driver driver;

    @OneToMany(mappedBy = "car", cascade = { CascadeType.PERSIST})
    @JsonIgnore
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;



}
