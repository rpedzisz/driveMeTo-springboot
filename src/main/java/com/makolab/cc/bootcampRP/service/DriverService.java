package com.makolab.cc.bootcampRP.service;

import com.makolab.cc.bootcampRP.dto.DriverDto;
import com.makolab.cc.bootcampRP.mapper.DriverMapper;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.DriverRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Driver Service class
 */
@Service
@Data
@Transactional
@Slf4j
public class DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final CourseRepository courseRepository;
    private final DriverMapper driverMapper;

    /**
     * Method to return all drivers
     * @return
     */
    public List<Driver> findAll(){
        return driverRepository.findAll();
    }

    /**
     * Update car status connected to driver, for example DRIVING or READY_TO_GO
     * @param driverId
     * @param carStatus
     * @return
     */
    public Car updateCarStatus(Long driverId, CarStatus carStatus) {
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if (driver != null && driver.getCar() != null) {
            Car car = carRepository.findById(driver.getCar().getCarId()).orElse(null);
            car.setCarStatus(carStatus);
            return car;
        }
        return null;
    }

    /**
     * Update localization of car connected to driver
     * @param driverId
     * @param localization
     * @return
     */
    public Car updateCarLocalization(Long driverId, String localization) {
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if (driver != null && driver.getCar() != null) {
            Car car = carRepository.findById(driver.getCar().getCarId()).orElse(null);
            car.setCurrentLocalization(localization);
            return car;
        }
        return null;
    }

    /**
     * Find all courses connected to specific driver
     * @param driverId
     * @return
     */
    public List<Course> findAllCoursesForDriver(Long driverId){
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if(driver!= null){
            return courseRepository.findAllByDriver(driver);
        }
        return new ArrayList<>();
    }

    /**
     * Update course status for specific driver
     * @param courseId
     * @param driverId
     * @param courseStatus
     * @return
     */
    public Course updateCourseStatus(Long courseId, Long driverId, CourseStatus courseStatus){
        Course course = courseRepository.findById(courseId).orElse(null);
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if(course != null
                && driver != null
                && driver.getCourses().contains(course)){
            course.setCourseStatus(courseStatus);
            return course;
        }
        return null;
    }

    /**
     * Return total number of all drivers
     * @return
     */
    public int calculateNumberOfDrivers(){
        return driverRepository.findAll().size();
    }

    /**
     * Method to add new drivers
     * @param driver
     * @return
     */
    public Driver addNewDriver(Driver driver){

        if(driver != null && driver.getDriverId() == null){
            return driverRepository.save(driver);
        }

        return null;

    }

    /**
     * Method that assigns drivers to cars
     * @param carId
     * @param driverId
     * @return
     */
    public Car assignDriverToCar(Long carId, Long driverId){

        Car car = carRepository.findById(carId).orElse(null);
        Driver driver = driverRepository.findById(driverId).orElse(null);

        if(car != null && driver != null){

            //car already had a driver
            if(car.getDriver() != null){
                car.getDriver().setCar(null);
            }
            //driver already had a car
            if(driver.getCar() != null){
                driver.getCar().setDriver(null);
                driver.getCar().setCarStatus(null);
            }

            car.setCarStatus(null);
            car.setDriver(driver);

            driver.setCar(car);
            return car;
        }

        return null;
    }

}
