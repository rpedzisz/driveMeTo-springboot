package com.makolab.cc.bootcampRP.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Car Dto
 */
@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDto {

    private Long carId;
    private String brand;
    private String model;
    private String currentLocalization;
    private Long driverId;
    private List<Course> courses;
    private CarStatus carStatus;

}
