package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-08T09:55:20+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.3 (Microsoft)"
)
@Component
public class RouteMapperImpl implements RouteMapper {

    @Override
    public RouteDto routeToDto(Route route) {
        if ( route == null ) {
            return null;
        }

        RouteDto routeDto = new RouteDto();

        routeDto.setRouteId( route.getId() );

        routeDto.setDistance( route.calculateTotalDistance() );
        routeDto.setAvgDuration( route.calculateTotalDuration() );

        return routeDto;
    }
}
