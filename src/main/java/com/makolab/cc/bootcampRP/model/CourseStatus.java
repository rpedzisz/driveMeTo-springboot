package com.makolab.cc.bootcampRP.model;

public enum CourseStatus {
    REQUESTED, //klient zamawia drivera/przejazd
    DRIVING_TO_CLIENT, // driver jedzie do klienta
    IN_PROGRESS, // w trakcie jazdy
    FINISHED, // trasa zakończona i zapłacono
    GRADED, //trasa zakończona oceniona

}
