package com.makolab.cc.bootcampRP.mapproviderrestclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TravelSummary {
    Long duration;
    Long length;
}
