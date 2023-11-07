package com.makolab.cc.bootcampRP.controller;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.mapper.CarMapper;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.service.CarService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Car Controller class
 */
@RestController
@Slf4j
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    /**
     * find all
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CarDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.findAll().stream()
                        .map(carMapper::carToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * find all by brand
     * @param brand
     * @return
     */
    @GetMapping("/by-brand/{brand}")
    public ResponseEntity<List<CarDto>> findAllByBrand(@PathVariable String brand) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.findAllByBrand(brand).stream()
                        .map(carMapper::carToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * find all by model
     * @param model
     * @return
     */
    @GetMapping("/by-model/{model}")
    public ResponseEntity<List<CarDto>> findAllByModel(@PathVariable String model) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.findAllByModel(model).stream()
                        .map(carMapper::carToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * find all by status
     * @param carStatus
     * @return
     */

    @GetMapping("/by-status/{carStatus}")
    public ResponseEntity<List<CarDto>> findAllByCarStatus(@PathVariable CarStatus carStatus) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.findAllByCarStatus(carStatus).stream()
                        .map(carMapper::carToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * count all cars
     * @return
     */

    @GetMapping("/count")
    public ResponseEntity<Integer> countCars(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.calculateNumberOfCars());
    }

    /**
     * sum of distance of all courses
     * @return
     */
    @GetMapping("/totaldistance")
    public ResponseEntity<Double> getTotalDistanceOfCars()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.calculateTotalDistanceOfCars());
    }

    /**
     * add new car
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<CarDto> saveCar(@RequestBody final CarDto dto) {
        log.info("SaveCar called");
        return ResponseEntity.status(HttpStatus.OK)
                .body(carMapper
                        .addCar_CarToDto(carService.addNewCar(carMapper.addCar_DtoToCar(dto))));
    }


}
