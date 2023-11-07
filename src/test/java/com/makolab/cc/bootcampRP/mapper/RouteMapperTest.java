package com.makolab.cc.bootcampRP.mapper;

import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Section;
import com.makolab.cc.bootcampRP.mapproviderrestclient.TravelSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class RouteMapperTest {

    private final RouteMapper routeMapper = Mappers.getMapper(RouteMapper.class);

    @Test
    void shouldMapCorrectlyrouteToDto(){
        Route route = new Route();
        route.setId("routeId");
        Section section = new Section();
        TravelSummary travelSummary = new TravelSummary();
        travelSummary.setDuration(600L);
        travelSummary.setLength(5000L);
        section.setTravelSummary(travelSummary);
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        route.setSections(sections);
        List<Route> routes = new ArrayList<>();
        routes.add(route);

        RouteDto routeDto = routeMapper.routeToDto(route);
        Assertions.assertEquals("routeId", routeDto.getRouteId());
        Assertions.assertEquals(5.0, routeDto.getDistance());
        Assertions.assertEquals(600L, routeDto.getAvgDuration());

    }

}
