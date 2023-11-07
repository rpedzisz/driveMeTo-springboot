package com.makolab.cc.bootcampRP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
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
import java.util.Set;

/**
 * Driver Model class
 */
@Entity
@Table(name = "driver")
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    private String name;

    @OneToOne
    @JoinColumn(name = "car_car_id")
    @JsonIgnore
    private Car car;

    private Double avgGrade = 0.0;

    @ElementCollection(targetClass=DriverLanguages.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<DriverLanguages> driverLanguagesSet;

    @OneToMany(mappedBy = "driver", cascade = { CascadeType.PERSIST})
    @JsonIgnore
    private List<Course> courses;

    public void calculateAvgGrade(){
        Long sum = 0L;
        for (Course course: courses) {
            sum+= course.getCourseGrade();
        }
        this.avgGrade = (sum.doubleValue()/courses.size());
    }


}
