package com.makolab.cc.bootcampRP.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * Driver Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDto {
    Long driverId;
    String name;
    Long carId;
    Double avgGrade;
    Set<DriverLanguages> driverLanguagesSet;
    List<Course> courses;
}
