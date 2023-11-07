package com.makolab.cc.bootcampRP.mapproviderrestclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Result {
    List<Route> routes;
}
