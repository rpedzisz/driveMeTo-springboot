package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CourseDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-08T09:55:20+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Microsoft)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDto courseToCourseDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseDto courseDto = new CourseDto();

        courseDto.setCourseId( entity.getCourseId() );
        courseDto.setCourseStatus( entity.getCourseStatus() );
        courseDto.setStartLocalization( entity.getStartLocalization() );
        courseDto.setChosenRoute( entity.getChosenRoute() );
        courseDto.setDestinationLocalization( entity.getDestinationLocalization() );
        courseDto.setDistance( entity.getDistance() );
        courseDto.setFee( entity.getFee() );
        courseDto.setCourseGrade( entity.getCourseGrade() );
        courseDto.setCarId( entityCarCarId( entity ) );
        courseDto.setDriverId( entityDriverDriverId( entity ) );
        courseDto.setUserId( entityUserUserId( entity ) );

        return courseDto;
    }

    private Long entityCarCarId(Course course) {
        if ( course == null ) {
            return null;
        }
        Car car = course.getCar();
        if ( car == null ) {
            return null;
        }
        Long carId = car.getCarId();
        if ( carId == null ) {
            return null;
        }
        return carId;
    }

    private Long entityDriverDriverId(Course course) {
        if ( course == null ) {
            return null;
        }
        Driver driver = course.getDriver();
        if ( driver == null ) {
            return null;
        }
        Long driverId = driver.getDriverId();
        if ( driverId == null ) {
            return null;
        }
        return driverId;
    }

    private Long entityUserUserId(Course course) {
        if ( course == null ) {
            return null;
        }
        User user = course.getUser();
        if ( user == null ) {
            return null;
        }
        Long userId = user.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
