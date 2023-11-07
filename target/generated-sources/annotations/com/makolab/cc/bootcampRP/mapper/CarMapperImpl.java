package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.Driver;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-08T09:55:20+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Microsoft)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Override
    public Car dtoToCar(CarDto dto) {
        if ( dto == null ) {
            return null;
        }

        Car car = new Car();

        car.setBrand( dto.getBrand() );
        car.setModel( dto.getModel() );
        car.setCarStatus( dto.getCarStatus() );
        car.setCarId( dto.getCarId() );
        car.setCurrentLocalization( dto.getCurrentLocalization() );
        List<Course> list = dto.getCourses();
        if ( list != null ) {
            car.setCourses( new ArrayList<Course>( list ) );
        }

        return car;
    }

    @Override
    public CarDto carToDto(Car entity) {
        if ( entity == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setBrand( entity.getBrand() );
        carDto.setModel( entity.getModel() );
        carDto.setCarStatus( entity.getCarStatus() );
        carDto.setDriverId( entityDriverDriverId( entity ) );
        carDto.setCarId( entity.getCarId() );
        carDto.setCurrentLocalization( entity.getCurrentLocalization() );
        List<Course> list = entity.getCourses();
        if ( list != null ) {
            carDto.setCourses( new ArrayList<Course>( list ) );
        }

        return carDto;
    }

    @Override
    public Car addCar_DtoToCar(CarDto dto) {
        if ( dto == null ) {
            return null;
        }

        Car car = new Car();

        car.setBrand( dto.getBrand() );
        car.setModel( dto.getModel() );
        car.setCarId( dto.getCarId() );
        car.setCurrentLocalization( dto.getCurrentLocalization() );
        List<Course> list = dto.getCourses();
        if ( list != null ) {
            car.setCourses( new ArrayList<Course>( list ) );
        }
        car.setCarStatus( dto.getCarStatus() );

        return car;
    }

    @Override
    public CarDto addCar_CarToDto(Car entity) {
        if ( entity == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setCarId( entity.getCarId() );
        carDto.setBrand( entity.getBrand() );
        carDto.setModel( entity.getModel() );
        carDto.setCurrentLocalization( entity.getCurrentLocalization() );
        List<Course> list = entity.getCourses();
        if ( list != null ) {
            carDto.setCourses( new ArrayList<Course>( list ) );
        }
        carDto.setCarStatus( entity.getCarStatus() );

        return carDto;
    }

    private Long entityDriverDriverId(Car car) {
        if ( car == null ) {
            return null;
        }
        Driver driver = car.getDriver();
        if ( driver == null ) {
            return null;
        }
        Long driverId = driver.getDriverId();
        if ( driverId == null ) {
            return null;
        }
        return driverId;
    }
}
