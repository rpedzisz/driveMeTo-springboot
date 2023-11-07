package com.makolab.cc.bootcampRP.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Course Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    Long courseId;
    CourseStatus courseStatus;

    String startLocalization;
    String chosenRoute;
    String destinationLocalization;

    Double distance;
    Double fee;
    Long courseGrade;

    Long carId;
    Long driverId;
    Long userId;
}
