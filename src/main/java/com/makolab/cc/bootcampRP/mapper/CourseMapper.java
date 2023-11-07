package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CourseDto;
import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Course model mapper
 */
@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mappings({
            @Mapping(target = "courseId", source = "entity.courseId"),
            @Mapping(target = "courseStatus", source = "entity.courseStatus"),
            @Mapping(target = "startLocalization", source = "entity.startLocalization"),
            @Mapping(target = "chosenRoute", source = "entity.chosenRoute"),
            @Mapping(target = "destinationLocalization", source = "entity.destinationLocalization"),
            @Mapping(target = "distance", source = "entity.distance"),
            @Mapping(target = "fee", source = "entity.fee"),
            @Mapping(target = "courseGrade", source = "entity.courseGrade"),
            @Mapping(target = "carId", source = "entity.car.carId"),
            @Mapping(target = "driverId", source = "entity.driver.driverId"),
            @Mapping(target = "userId", source = "entity.user.userId"),
    })
    CourseDto courseToCourseDto(Course entity);


}
