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
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@TestPropertySource(locations = "classpath:tests.properties")
@Transactional
public class DriverTest {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CourseRepository courseRepository;


    Driver driver;

    @BeforeEach
    void init(){
        driverRepository.deleteAll();
        carRepository.deleteAll();
        driver = new Driver();
        driver.setName("Adam");
        driver.setAvgGrade(5.0);

    }

    @Test
    void savingDriverWithLanguagesShouldConnectLanguagesToDriver(){
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driver.setDriverLanguagesSet(driverLanguagesSet);
        driverRepository.save(driver);

        Driver actualDriver = driverRepository.findById(driver.getDriverId()).orElse(null);
        Assertions.assertNotNull(actualDriver);
        Assertions.assertEquals(driverLanguagesSet, actualDriver.getDriverLanguagesSet());

    }

    @Test
    void deletingDriverShouldNotDeleteCar(){
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCurrentLocalization("51.250725,22.510415");
        carRepository.save(car);
        driver.setCar(car);
        driverRepository.save(driver);

        driverRepository.deleteById(driver.getDriverId());

        Assertions.assertNull(driverRepository.findById(driver.getDriverId()).orElse(null));
        Assertions.assertNotNull(carRepository.findById(car.getCarId()).orElse(null));

    }

    @Test
    void deletingDriverShouldNotDeleteCourses(){

        Course course = new Course();
        course.setCourseGrade(5L);
        courseRepository.save(course);


        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course);

        driver.setCourses(courses);
        driverRepository.save(driver);
        course.setDriver(driver);

        driverRepository.deleteById(driver.getDriverId());
        Assertions.assertNull(driverRepository.findById(driver.getDriverId()).orElse(null));
        Assertions.assertNotNull(courseRepository.findById(course.getCourseId()).orElse(null));

    }




}
