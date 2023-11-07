package com.makolab.cc.bootcampRP.service;

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

import java.util.List;
import java.util.Optional;

/**
 * Car service class
 */
@Service
@Data
@Slf4j
public class CarService {

    private final CarRepository carRepository;
    private final CourseRepository courseRepository;

    /**
     * Method to return all cars from database
     * @return
     */
    public List<Car> findAll() {return carRepository.findAll();}

    /**
     * Method to return all cars with specific brand
     * @param brand
     * @return
     */
    public List<Car> findAllByBrand(String brand){
        return carRepository.findAllByBrand(brand);
    }

    /**
     * Method to return all cars with specific model
     * @param model
     * @return
     */
    public List<Car> findAllByModel(String model){return carRepository.findAllByModel(model);};

    /**
     * Method to return all cars with specific CarStatus
     * @param carStatus
     * @return
     */
    public List<Car> findAllByCarStatus(CarStatus carStatus){return carRepository.findAllByCarStatus(carStatus);};

    /**
     * Calculate distance of all cars based on connected courses
     * @return
     */
    public Double calculateTotalDistanceOfCars(){
        log.info("calculateTotalDistanceOfCars called");
        List<Course> courses = courseRepository.findAll();
        Double totalKM = 0.0;
        for(Course course: courses){
            if(course.getCar() != null
                    && (course.getCourseStatus() == CourseStatus.FINISHED
                    || course.getCourseStatus() ==CourseStatus.GRADED)){
                totalKM+= course.getDistance();
            }
        }

        return totalKM;
    }

    /**
     * Calculate total number of all cars
     * @return
     */
    public int calculateNumberOfCars(){
        return carRepository.findAll().size();
    }

    /**
     * Method to add cars
     * @param car
     * @return
     */
    public Car addNewCar(Car car){

        log.info("Add new Car called");
        if(car != null && car.getCarId() == null) {

            car.setCarStatus(null);
            car.setDriver(null);
            car.setCurrentLocalization(null);
            carRepository.save(car);
            log.info("Car saved: " + car.toString());
            return car;
        }
        return null;
    }



}
