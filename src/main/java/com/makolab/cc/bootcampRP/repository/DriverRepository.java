package com.makolab.cc.bootcampRP.repository;

import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Driver Repository interface
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByCarIn(List<Car> car);
    List<Driver> findAllByDriverLanguagesSet(DriverLanguages driverLanguages);
    List<Driver> findAllByDriverLanguagesSetAndCarIn(DriverLanguages driverLanguages, List<Car> car);
}
