package com.makolab.cc.bootcampRP.mapproviderrestclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static java.lang.Math.round;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Route {
    String id;
    List<Section> sections;

    public double calculateTotalDistance(){
        Long sum  = 0L;
        for (Section section: sections){
            sum+=section.travelSummary.length;
        }
        return sum.doubleValue()/1000;
    }
    public double calculateTotalDuration(){
        Long sum  = 0L;
        for (Section section: sections){
            sum+=section.travelSummary.duration;
        }
        return sum.doubleValue();
    }


}
