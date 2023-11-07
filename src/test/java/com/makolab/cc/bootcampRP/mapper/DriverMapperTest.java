package com.makolab.cc.bootcampRP.mapper;


import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

public class DriverMapperTest {

    private final DriverMapper driverMapper = Mappers.getMapper(DriverMapper.class);

    @Test
    void shouldMapCorrectlyDriverToDriverDistanceDto(){
        Driver driver = new Driver();
        driver.setName("Adam");
        driver.setDriverId(1L);
        driver.setAvgGrade(5.0);

        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);

        driver.setCar(car);
        driver.setDriverLanguagesSet(driverLanguagesSet);
        Double distanceToClient = 5.0;

        DriverDistanceDto driverDistanceDto = driverMapper.objectToDriverDistanceDto(driver, distanceToClient);

        Assertions.assertEquals(1L, driverDistanceDto.getDriverId());
        Assertions.assertEquals("Adam", driverDistanceDto.getName());
        Assertions.assertEquals(car, driverDistanceDto.getCar());
        Assertions.assertEquals(5.0, driverDistanceDto.getAvgGrade());
        Assertions.assertEquals(driverLanguagesSet, driverDistanceDto.getDriverLanguagesSet());
        Assertions.assertEquals(5.0, driverDistanceDto.getDistanceToClient());


    }
    @Test
    void shouldMapCorrectlyfindAllDriverToDriverDto() {
        Driver driver = new Driver();
        driver.setName("Adam");
        driver.setDriverId(1L);
        driver.setAvgGrade(5.0);

        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);

        driver.setCar(car);
        driver.setDriverLanguagesSet(driverLanguagesSet);

        DriverDto driverDto = driverMapper.findAll_DriverToDto(driver);

        Assertions.assertEquals(1L, driverDto.getDriverId());
        Assertions.assertEquals("Adam", driverDto.getName());
        Assertions.assertEquals(1L, driverDto.getCarId());
        Assertions.assertEquals(driverLanguagesSet, driverDto.getDriverLanguagesSet());
    }

    @Test
    void shouldMapCorrectlyaddDriver_DtoToDriver(){
        DriverDto driverDto = new DriverDto();
        driverDto.setName("adam");
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        driverDto.setDriverLanguagesSet(driverLanguagesSet);

        Driver driver = driverMapper.addDriver_DtoToDriver(driverDto);

        Assertions.assertEquals("adam", driverDto.getName());
        Assertions.assertEquals(driverLanguagesSet, driverDto.getDriverLanguagesSet());
    }

    @Test
    void shouldMapCorrectlyaddDriver_DriverToDto() {
        Driver driver = new Driver();
        driver.setName("Adam");
        driver.setDriverId(1L);

        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);

        driver.setCar(car);
        driver.setDriverLanguagesSet(driverLanguagesSet);

        DriverDto driverDto = driverMapper.addDriver_DriverToDto(driver);

        Assertions.assertEquals(1L, driverDto.getDriverId());
        Assertions.assertEquals("Adam", driverDto.getName());
        Assertions.assertEquals(1L, driverDto.getCarId());
        Assertions.assertEquals(driverLanguagesSet, driverDto.getDriverLanguagesSet());
    }




}
