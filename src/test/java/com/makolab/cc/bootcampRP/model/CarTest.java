package com.makolab.cc.bootcampRP.model;

import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.DriverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:tests.properties")
@Transactional
public class CarTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CourseRepository courseRepository;

    Car car;
    @BeforeEach
    void init(){
        carRepository.deleteAll();
        driverRepository.deleteAll();
        courseRepository.deleteAll();
        car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCurrentLocalization("51.250725,22.510415");

    }

    @Test
    void savingCarWithStatusShouldConnectCarStatusWithCar(){
        car.setCarStatus(CarStatus.DRIVING);
        carRepository.save(car);

        Car actual = carRepository.findById(car.getCarId()).orElse(null);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(CarStatus.DRIVING, actual.getCarStatus());
    }

    @Test
    void savingCarShouldNotAddDriver(){
        carRepository.save(car);
        Car actual = carRepository.findById(car.getCarId()).orElse(null);
        Assertions.assertNull(actual.getDriver());
    }

    @Test
    void deletingCarShouldNotDeleteDriver(){
        Driver driver = new Driver();
        driver.setName("Adam");

        driverRepository.save(driver);
        carRepository.save(car);

        car.setDriver(driver);
        driver.setCar(car);


        carRepository.deleteById(car.getCarId());

        Assertions.assertNull(carRepository.findById(car.getCarId()).orElse(null));
        Assertions.assertNotNull(driverRepository.findById(driver.getDriverId()).orElse(null));
    }

    @Test
    void deletingCarShouldNotDeleteCourses(){

        Course course = new Course();
        course.setCourseGrade(5L);
        courseRepository.save(course);


        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);
        car.setCourses(courses);
        carRepository.save(car);
        course.setCar(car);

        carRepository.deleteById(car.getCarId());
        Assertions.assertNull(carRepository.findById(car.getCarId()).orElse(null));
        Assertions.assertNotNull(courseRepository.findById(course.getCourseId()).orElse(null));

    }




}
