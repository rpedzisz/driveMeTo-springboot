package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-08T09:55:20+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Microsoft)"
)
@Component
public class DriverMapperImpl implements DriverMapper {

    @Override
    public DriverDistanceDto objectToDriverDistanceDto(Driver entity, Double distanceToClient) {
        if ( entity == null && distanceToClient == null ) {
            return null;
        }

        DriverDistanceDto driverDistanceDto = new DriverDistanceDto();

        if ( entity != null ) {
            driverDistanceDto.setDriverId( entity.getDriverId() );
            driverDistanceDto.setName( entity.getName() );
            driverDistanceDto.setCar( entity.getCar() );
            driverDistanceDto.setAvgGrade( entity.getAvgGrade() );
            Set<DriverLanguages> set = entity.getDriverLanguagesSet();
            if ( set != null ) {
                driverDistanceDto.setDriverLanguagesSet( new LinkedHashSet<DriverLanguages>( set ) );
            }
        }
        driverDistanceDto.setDistanceToClient( distanceToClient );

        return driverDistanceDto;
    }

    @Override
    public DriverDto findAll_DriverToDto(Driver entity) {
        if ( entity == null ) {
            return null;
        }

        DriverDto driverDto = new DriverDto();

        driverDto.setDriverId( entity.getDriverId() );
        driverDto.setName( entity.getName() );
        Set<DriverLanguages> set = entity.getDriverLanguagesSet();
        if ( set != null ) {
            driverDto.setDriverLanguagesSet( new LinkedHashSet<DriverLanguages>( set ) );
        }
        driverDto.setCarId( entityCarCarId( entity ) );
        driverDto.setAvgGrade( entity.getAvgGrade() );
        List<Course> list = entity.getCourses();
        if ( list != null ) {
            driverDto.setCourses( new ArrayList<Course>( list ) );
        }

        return driverDto;
    }

    @Override
    public Driver addDriver_DtoToDriver(DriverDto dto) {
        if ( dto == null ) {
            return null;
        }

        Driver driver = new Driver();

        driver.setName( dto.getName() );
        Set<DriverLanguages> set = dto.getDriverLanguagesSet();
        if ( set != null ) {
            driver.setDriverLanguagesSet( new LinkedHashSet<DriverLanguages>( set ) );
        }
        driver.setDriverId( dto.getDriverId() );
        driver.setAvgGrade( dto.getAvgGrade() );
        List<Course> list = dto.getCourses();
        if ( list != null ) {
            driver.setCourses( new ArrayList<Course>( list ) );
        }

        return driver;
    }

    @Override
    public DriverDto addDriver_DriverToDto(Driver entity) {
        if ( entity == null ) {
            return null;
        }

        DriverDto driverDto = new DriverDto();

        driverDto.setDriverId( entity.getDriverId() );
        driverDto.setName( entity.getName() );
        Set<DriverLanguages> set = entity.getDriverLanguagesSet();
        if ( set != null ) {
            driverDto.setDriverLanguagesSet( new LinkedHashSet<DriverLanguages>( set ) );
        }
        driverDto.setCarId( entityCarCarId( entity ) );
        driverDto.setAvgGrade( entity.getAvgGrade() );
        List<Course> list = entity.getCourses();
        if ( list != null ) {
            driverDto.setCourses( new ArrayList<Course>( list ) );
        }

        return driverDto;
    }

    private Long entityCarCarId(Driver driver) {
        if ( driver == null ) {
            return null;
        }
        Car car = driver.getCar();
        if ( car == null ) {
            return null;
        }
        Long carId = car.getCarId();
        if ( carId == null ) {
            return null;
        }
        return carId;
    }
}
