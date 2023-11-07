package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public class CarMapperTest {
    private final CarMapper carMapper= Mappers.getMapper(CarMapper.class);


    @Test
    void shouldMapCorrectlydtoToCar(){
        CarDto carDto = new CarDto();
        carDto.setBrand("Mercedes");
        carDto.setModel("Sedan");
        carDto.setCarStatus(CarStatus.READY_TO_GO);

        Car car = carMapper.dtoToCar(carDto);

        Assertions.assertEquals("Mercedes", car.getBrand());
        Assertions.assertEquals("Sedan", car.getModel());
        Assertions.assertEquals(CarStatus.READY_TO_GO, car.getCarStatus());
    }

    @Test
    void shouldMapCorrectlycarToDto(){
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCarStatus(CarStatus.READY_TO_GO);
        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setCar(car);
        car.setDriver(driver);

        CarDto carDto = carMapper.carToDto(car);

        Assertions.assertEquals("Mercedes", carDto.getBrand());
        Assertions.assertEquals("Sedan", carDto.getModel());
        Assertions.assertEquals(CarStatus.READY_TO_GO, carDto.getCarStatus());
        Assertions.assertEquals(1L, carDto.getDriverId());
    }

    @Test
    void shouldMapCorrectlyAddCar_dtoToCar(){
        CarDto carDto = new CarDto();
        carDto.setBrand("Mercedes");
        carDto.setModel("Sedan");

        Car car = carMapper.dtoToCar(carDto);

        Assertions.assertEquals("Mercedes", car.getBrand());
        Assertions.assertEquals("Sedan", car.getModel());
    }

    @Test
    void shouldMapCorrectlyAddCar_carToDto(){
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");


        CarDto carDto = carMapper.carToDto(car);

        Assertions.assertEquals("Mercedes", carDto.getBrand());
        Assertions.assertEquals("Sedan", carDto.getModel());
        Assertions.assertEquals(1L, carDto.getCarId());
    }


}
