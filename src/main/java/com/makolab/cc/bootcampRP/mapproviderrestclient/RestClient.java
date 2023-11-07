package com.makolab.cc.bootcampRP.mapproviderrestclient;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Restclient for HERE maps Api
 * @author rafal.pedzisz
 */
@Slf4j
@Data
@Component
public class RestClient {

    private final RestTemplate restTemplate = new RestTemplate();


    String apikey = "oNWhKdxCEBwTIrCh93QsVVvWw0HTPx3WHKgqXuiDxu0";


    /**
     * Returns List<Route> from here maps api
     * @param origin
     * @param destination
     * @return
     */
    public  List<Route> getRoutesListBetween(String origin, String destination){

        String getURL =
                "https://router.hereapi.com/v8/routes?" +
                        "transportMode=car" +
                        "&origin="+origin+
                        "&destination="+destination+
                        "&return=travelSummary" +
                        "&alternatives=5" +
                        "&apikey=" + apikey;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", httpHeaders);

        return restTemplate.exchange(getURL,
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Result>() {
                }).getBody().routes;
    }


    /**
     * returns best route distance between two coordinates
     * @param origin
     * @param destination
     * @return
     */
    public Double getDistanceBetween(String origin, String destination){

        String getURL =
                "https://router.hereapi.com/v8/routes?" +
                        "transportMode=car" +
                        "&origin="+origin+
                        "&destination="+destination+
                        "&alternatives=0" +
                        "&return=travelSummary" +
                        "&apikey=" + apikey;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", httpHeaders);

        List<Route> routes = restTemplate.exchange(getURL,
                HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Result>() {
                }).getBody().routes;
        Route route = routes.get(0);

        return route.calculateTotalDistance();
    }




    }






