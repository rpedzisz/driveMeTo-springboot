package com.makolab.cc.bootcampRP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Course Model class
 */
@Entity
@Table(name = "course")
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long courseId;

    @Enumerated(EnumType.STRING)
    CourseStatus courseStatus;

    String startLocalization;
    String chosenRoute;
    String destinationLocalization;

    Double distance;

    Double fee = 0.0;

    Long courseGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    User user;




}
