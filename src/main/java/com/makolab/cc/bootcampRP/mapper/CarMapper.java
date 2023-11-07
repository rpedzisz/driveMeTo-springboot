package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
/**
 * Car Model Mapper
 */
@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mappings({
            @Mapping(target = "brand", source = "dto.brand"),
            @Mapping(target = "model", source = "dto.model"),
            @Mapping(target = "carStatus", source = "dto.carStatus"),


    })
    Car dtoToCar(CarDto dto);
    @Mappings({
            @Mapping(target = "brand", source = "entity.brand"),
            @Mapping(target = "model", source = "entity.model"),
            @Mapping(target = "carStatus", source = "entity.carStatus"),
            @Mapping(target = "driverId", source = "entity.driver.driverId"),

    })
    CarDto carToDto(Car entity);


    @Mappings({
            @Mapping(target = "brand", source = "dto.brand"),
            @Mapping(target = "model", source = "dto.model"),

    })
    Car addCar_DtoToCar(CarDto dto);

    @Mappings({
            @Mapping(target = "carId", source = "entity.carId"),
            @Mapping(target = "brand", source = "entity.brand"),
            @Mapping(target = "model", source = "entity.model"),

    })
    CarDto addCar_CarToDto(Car entity);




}
