package com.makolab.cc.bootcampRP.mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.makolab.cc.bootcampRP.dto.CourseDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

public class CourseMapperTest {
    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @Test
   void shouldMapCorrectlycourseToCourseDto(){
        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseStatus(CourseStatus.FINISHED);
        course.setStartLocalization("start");
        course.setChosenRoute("routeId");
        course.setDestinationLocalization("end");
        course.setDistance(5.0);
        course.setFee(25.0);
        course.setCourseGrade(5L);
        Car car = new Car();
        car.setCarId(1L);
        Driver driver = new Driver();
        driver.setDriverId(1L);
        User user = new User();
        user.setUserId(1L);
        course.setCar(car);
        course.setDriver(driver);
        course.setUser(user);

        CourseDto courseDto = courseMapper.courseToCourseDto(course);
        Assertions.assertEquals(1L, courseDto.getCourseId());
        Assertions.assertEquals(CourseStatus.FINISHED, courseDto.getCourseStatus());
        Assertions.assertEquals("start", courseDto.getStartLocalization());
        Assertions.assertEquals("routeId", courseDto.getChosenRoute());
        Assertions.assertEquals("end", courseDto.getDestinationLocalization());
        Assertions.assertEquals(5.0, courseDto.getDistance());
        Assertions.assertEquals(25.0, courseDto.getFee());
        Assertions.assertEquals(5L, courseDto.getCourseGrade());
        Assertions.assertEquals(1L, courseDto.getCarId());
        Assertions.assertEquals(1L, courseDto.getDriverId());
        Assertions.assertEquals(1L, courseDto.getUserId());
    }


}
