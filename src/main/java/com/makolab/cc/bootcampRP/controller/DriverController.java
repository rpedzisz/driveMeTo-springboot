package com.makolab.cc.bootcampRP.controller;

import com.makolab.cc.bootcampRP.dto.CarDto;
import com.makolab.cc.bootcampRP.dto.CourseDto;
import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.mapper.CarMapper;
import com.makolab.cc.bootcampRP.mapper.CourseMapper;
import com.makolab.cc.bootcampRP.mapper.DriverMapper;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.service.CourseService;
import com.makolab.cc.bootcampRP.service.DriverService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Driver Controller class
 */
@RestController
@Slf4j
@RequestMapping("/drivers")
@AllArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;
    private final CarMapper carMapper;
    private final CourseMapper courseMapper;


    /**
     * Find all drivers
     * @return
     */
    @GetMapping
    public ResponseEntity<List<DriverDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(driverService.findAll().stream()
                        .map(driverMapper::findAll_DriverToDto)
                        .collect(Collectors.toList()));
    }

    /**
     *  Count drivers
     * @return
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> countDrivers(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(driverService.calculateNumberOfDrivers());
    }

    /**
     * Add new driver
     * {
     * "name": "drivername"
     * }
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<DriverDto> addDriver(@RequestBody final DriverDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(driverMapper
                        .addDriver_DriverToDto(driverService.addNewDriver(driverMapper.addDriver_DtoToDriver(dto))));
    }

    /**
     * Update driver car status - DRIVING,READY_TO_GO
     * @param driverId
     * @param carStatus
     * @return
     */
    @PostMapping("/update-car-status/{driverId}/{carStatus}")
    public ResponseEntity <CarDto> updateCarStatus(@PathVariable Long driverId,
                                                   @PathVariable CarStatus carStatus){
        return ResponseEntity.status(HttpStatus.OK)
                .body(carMapper.carToDto(driverService.updateCarStatus(driverId, carStatus)));

    }

    /**
     * Update car localization(coordinates) for example "51.250242,22.512197"
     * @param driverId
     * @param localization
     * @return
     */
    @PostMapping("/update-car-localization/{driverId}/{localization}")
    public ResponseEntity <CarDto> updateCarLocalization(@PathVariable Long driverId,
                                                   @PathVariable String localization){
        return ResponseEntity.status(HttpStatus.OK)
                .body(carMapper.carToDto(driverService.updateCarLocalization(driverId, localization)));

    }

    /**
     * Find all courses for specific driver
     * @param driverId
     * @return
     */
    @GetMapping("/get-courses-for-driver/{driverId}")
    public ResponseEntity <List<CourseDto>> getCoursesForDriver(@PathVariable Long driverId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(driverService.findAllCoursesForDriver(driverId).stream()
                        .map(courseMapper::courseToCourseDto)
                        .collect(Collectors.toList()));

    }

    /**
     * Update Status of Course for Driver, for example FINISHED
     * @param courseId
     * @param driverId
     * @param courseStatus
     * @return
     */
    @PostMapping("/update-course-status/{courseId}/{driverId}/{courseStatus}")
    public ResponseEntity <CourseDto> updateCarLocalization(@PathVariable Long courseId,
                                                         @PathVariable Long driverId,
                                                         @PathVariable CourseStatus courseStatus){
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseMapper.courseToCourseDto(driverService.updateCourseStatus(courseId,driverId,courseStatus)));

    }

    /**
     * Assign Driver to Car
     * @param driverId
     * @param carId
     * @return
     */
    @PostMapping("/assign-driver/{driverId}/{carId}")
    public ResponseEntity<CarDto> assignDriverToCard(@PathVariable Long driverId,
                                                     @PathVariable Long carId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(carMapper.carToDto(driverService.assignDriverToCar(carId, driverId)));
    }





}