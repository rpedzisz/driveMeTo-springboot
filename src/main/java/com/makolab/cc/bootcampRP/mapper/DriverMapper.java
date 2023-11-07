package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Driver model mapper
 */
@Mapper(componentModel = "spring")
public interface DriverMapper {

        @Mappings({
                @Mapping(target = "driverId", source = "entity.driverId"),
                @Mapping(target = "name", source = "entity.name"),
                @Mapping(target = "car", source = "entity.car"),
                @Mapping(target = "avgGrade", source = "entity.avgGrade"),
                @Mapping(target = "driverLanguagesSet", source = "entity.driverLanguagesSet"),
                @Mapping(target = "distanceToClient", source = "distanceToClient")
        })
        DriverDistanceDto objectToDriverDistanceDto(Driver entity, Double distanceToClient);

            @Mappings({
                    @Mapping(target = "driverId", source = "entity.driverId"),
                    @Mapping(target = "name", source = "entity.name"),
                    @Mapping(target = "driverLanguagesSet", source = "entity.driverLanguagesSet"),
                    @Mapping(target = "carId", source = "entity.car.carId"),

            })
            DriverDto findAll_DriverToDto(Driver entity);


        @Mappings({
                @Mapping(target = "name", source = "dto.name"),
                @Mapping(target = "driverLanguagesSet", source = "dto.driverLanguagesSet"),

        })
        Driver addDriver_DtoToDriver(DriverDto dto);
        @Mappings({
                @Mapping(target = "driverId", source = "entity.driverId"),
                @Mapping(target = "name", source = "entity.name"),
                @Mapping(target = "driverLanguagesSet", source = "entity.driverLanguagesSet"),
                @Mapping(target = "carId", source = "entity.car.carId"),

        })
        DriverDto addDriver_DriverToDto(Driver entity);


}

