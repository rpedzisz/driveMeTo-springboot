package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Route model mapper
 */
@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mappings({
            @Mapping(target = "routeId", source = "route.id"),
            @Mapping(target = "distance", expression = "java(route.calculateTotalDistance())"),
            @Mapping(target = "avgDuration", expression = "java(route.calculateTotalDuration())"),

    })
    RouteDto routeToDto(Route route);
}
