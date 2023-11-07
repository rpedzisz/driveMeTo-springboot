package com.makolab.cc.bootcampRP.service;

import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.DriverRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @InjectMocks
    DriverService driverService;

    @Mock
    DriverRepository driverRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    CourseRepository courseRepository;

    @Test
    void findAllShouldReturnAllDrivers(){
        Driver driver1 = new Driver();
        driver1.setName("Adam");
        driver1.setAvgGrade(3.0);

        Driver driver2 = new Driver();
        driver2.setName("Michał");
        driver2.setAvgGrade(5.0);

        List<Driver> expected = new ArrayList<>();
        expected.add(driver1);
        expected.add(driver2);

        when(driverRepository.findAll()).thenReturn(expected);
        Assertions.assertEquals(expected, driverService.findAll());

    }





    @Test
    void updateCarStatusShouldUpdateToREADY_TO_GO(){
        Car car = new Car();
        car.setCarId(1L);
        car.setCarStatus(CarStatus.DRIVING);
        car.setBrand("Mercedes");
        car.setModel("Sedan");

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);
        car.setDriver(driver);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car updatedCar = driverService.updateCarStatus(1L, CarStatus.READY_TO_GO);

        Assertions.assertEquals(CarStatus.READY_TO_GO, updatedCar.getCarStatus());

    }

    @Test
    void updateCarStatusShouldUpdateToNewLocation(){
        Car car = new Car();
        car.setCarId(1L);
        car.setCarStatus(CarStatus.DRIVING);
        car.setCurrentLocalization("51.756434,19.434556"); // Łódź
        car.setBrand("Mercedes");
        car.setModel("Sedan");

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(car);
        car.setDriver(driver);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car updatedCar = driverService.updateCarLocalization(1L, "90.000000,45.000000");//biegun południowy

        Assertions.assertEquals("90.000000,45.000000", updatedCar.getCurrentLocalization());

    }

    @Test
    void findAllCoursesShouldReturnAllCoursesForDriver(){
        Driver driver1 = new Driver();
        driver1.setName("Adam");
        driver1.setAvgGrade(3.0);

        Course course = new Course();
        course.setCourseGrade(5L);
        course.setDistance(5.4);
        course.setDriver(driver1);

        Course course1 = new Course();
        course1.setCourseGrade(2L);
        course1.setDistance(15.4);
        course1.setDriver(driver1);

        List<Course> expected = new ArrayList<>();
        expected.add(course);
        expected.add(course1);



        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver1));
        when(courseRepository.findAllByDriver(driver1)).thenReturn(expected);

        Assertions.assertEquals(expected, driverService.findAllCoursesForDriver(1L));

    }

    @Test
    void updateCourseStatusShouldUpdateStatusToFINISHED(){
        Driver driver1 = new Driver();
        driver1.setDriverId(1L);
        driver1.setName("Adam");
        driver1.setAvgGrade(3.0);

        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseStatus(CourseStatus.IN_PROGRESS);
        course.setCourseGrade(5L);
        course.setDistance(5.4);
        course.setDriver(driver1);

        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        driver1.setCourses(courseList);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver1));

        Course updatedCourse = driverService.updateCourseStatus(1L, 1L, CourseStatus.FINISHED);
        Assertions.assertEquals(CourseStatus.FINISHED, updatedCourse.getCourseStatus());
    }

    @Test
    void calculateNumberOfDriversShouldReturn2(){
        Driver driver1 = new Driver();
        driver1.setName("Adam");
        driver1.setAvgGrade(3.0);

        Driver driver2 = new Driver();
        driver2.setName("Michał");
        driver2.setAvgGrade(5.0);

        List<Driver> driverList = new ArrayList<>();
        driverList.add(driver1);
        driverList.add(driver2);

        when(driverRepository.findAll()).thenReturn(driverList);

        Assertions.assertEquals(2, driverService.calculateNumberOfDrivers());

    }

    @Test
    void addDriverShouldReturnCreatedDriver(){
        Driver newDriver = new Driver();
        newDriver.setName("Adam");

        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        driverLanguagesSet.add(DriverLanguages.POLISH);
        newDriver.setDriverLanguagesSet(driverLanguagesSet);

        when(driverRepository.save(any(Driver.class))).thenReturn(newDriver);
        Driver createdDriver = driverService.addNewDriver(newDriver);

        Assertions.assertEquals("Adam", createdDriver.getName());
        Assertions.assertEquals(0.0, createdDriver.getAvgGrade());
        Assertions.assertEquals(driverLanguagesSet, createdDriver.getDriverLanguagesSet());
        Assertions.assertNull(createdDriver.getCar());
        Assertions.assertNull(createdDriver.getCourses());

    }

    @Test
    void assignDriverToCarShouldCorrectlySetIdValues(){
        Car car = new Car();
        car.setCarId(1L);
        car.setCarStatus(CarStatus.DRIVING);
        car.setCurrentLocalization("51.756434,19.434556"); // Łódź
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setDriver(null);

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setCar(null);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Car assignedCar = driverService.assignDriverToCar(1L, 1L);

        Assertions.assertEquals(driver, assignedCar.getDriver());
        Assertions.assertEquals(car, driver.getCar());
        Assertions.assertEquals(1L, assignedCar.getDriver().getDriverId());
        Assertions.assertEquals(1L, driver.getCar().getCarId());

    }

    @Test
    void assignDriverWithCarToNewCarShouldCorrectlyRemoveOldCarIdAndAssignNew(){
        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");

        car.setDriver(driver);
        driver.setCar(car);

        Car newCar = new Car();
        newCar.setCarId(2L);
        newCar.setBrand("Fiat");
        newCar.setModel("126p");


        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        when(carRepository.findById(2L)).thenReturn(Optional.of(newCar));

        Car assignedCar = driverService.assignDriverToCar(2L, 1L);

        Assertions.assertEquals(driver, assignedCar.getDriver());
        Assertions.assertNull(car.getDriver());
        Assertions.assertEquals(1L, assignedCar.getDriver().getDriverId());
        Assertions.assertEquals(2L, driver.getCar().getCarId());



    }


}
