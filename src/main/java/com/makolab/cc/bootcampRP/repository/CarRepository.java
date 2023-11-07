package com.makolab.cc.bootcampRP.repository;

import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Car Repository interface
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByBrand(String brand);
    List<Car> findAllByModel(String model);
    List<Car> findAllByCarStatus(CarStatus carStatus);
    List<Car> findAllByBrandAndCarStatus(String brand, CarStatus carStatus);
}
