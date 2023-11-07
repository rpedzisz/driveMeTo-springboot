package com.makolab.cc.bootcampRP.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DriverDistance Dto
 */
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDistanceDto {
    private Long driverId;
    private String name;
    private Car car;
    private Double avgGrade;
    private Set<DriverLanguages> driverLanguagesSet;
    private Double distanceToClient;
}
