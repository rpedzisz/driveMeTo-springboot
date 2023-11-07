package com.makolab.cc.bootcampRP.service;

import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.User;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @InjectMocks
    CarService carService;

    @Mock
    CarRepository carRepository;

    @Mock
    CourseRepository courseRepository;


    @Test
    void findAllShouldReturnAllCars(){
        Car car1 = new Car();
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");

        Car car2 = new Car();
        car2.setCarStatus(CarStatus.READY_TO_GO);
        car2.setBrand("Fiat");
        car2.setModel("125p");

        List<Car> expectedCarList = new ArrayList<>();
        expectedCarList.add(car1);
        expectedCarList.add(car2);

        when(carRepository.findAll()).thenReturn(expectedCarList);
        List<Car> actualCarList = carService.findAll();

        Assertions.assertEquals(expectedCarList, actualCarList);
        Assertions.assertEquals(CarStatus.DRIVING, actualCarList.get(0).getCarStatus());
        Assertions.assertEquals("Mercedes", actualCarList.get(0).getBrand());
        Assertions.assertEquals("Sedan", actualCarList.get(0).getModel());
        Assertions.assertEquals(CarStatus.READY_TO_GO, actualCarList.get(1).getCarStatus());
        Assertions.assertEquals("Fiat", actualCarList.get(1).getBrand());
        Assertions.assertEquals("125p", actualCarList.get(1).getModel());

    }

    @Test
    void findAllByBrandMercedesShouldReturnAllMercedesCars(){
        Car car1 = new Car();
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");

        Car car2 = new Car();
        car2.setCarStatus(CarStatus.READY_TO_GO);
        car2.setBrand("Fiat");
        car2.setModel("125p");

        List<Car> expectedCarList = new ArrayList<>();
        expectedCarList.add(car1);


        when(carRepository.findAllByBrand("Mercedes")).thenReturn(expectedCarList);
        List<Car> actualCarList = carService.findAllByBrand("Mercedes");

        Assertions.assertEquals(expectedCarList, actualCarList);
        Assertions.assertEquals(CarStatus.DRIVING, actualCarList.get(0).getCarStatus());
        Assertions.assertEquals("Mercedes", actualCarList.get(0).getBrand());
        Assertions.assertEquals("Sedan", actualCarList.get(0).getModel());

    }
    @Test
    void findAllByModel125pShouldReturnAllFiat125Cars(){
        Car car1 = new Car();
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");

        Car car2 = new Car();
        car2.setCarStatus(CarStatus.READY_TO_GO);
        car2.setBrand("Fiat");
        car2.setModel("125p");

        List<Car> expectedCarList = new ArrayList<>();
        expectedCarList.add(car2);


        when(carRepository.findAllByModel("125p")).thenReturn(expectedCarList);
        List<Car> actualCarList = carService.findAllByModel("125p");

        Assertions.assertEquals(expectedCarList, actualCarList);
        Assertions.assertEquals(CarStatus.READY_TO_GO, actualCarList.get(0).getCarStatus());
        Assertions.assertEquals("Fiat", actualCarList.get(0).getBrand());
        Assertions.assertEquals("125p", actualCarList.get(0).getModel());

    }

    @Test
    void findAllDrivingStatusShouldReturnMercedesCar(){
        Car car1 = new Car();
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");

        Car car2 = new Car();
        car2.setCarStatus(CarStatus.READY_TO_GO);
        car2.setBrand("Fiat");
        car2.setModel("125p");

        List<Car> expectedCarList = new ArrayList<>();
        expectedCarList.add(car1);


        when(carRepository.findAllByCarStatus(CarStatus.DRIVING)).thenReturn(expectedCarList);
        List<Car> actualCarList = carService.findAllByCarStatus(CarStatus.DRIVING);

        Assertions.assertEquals(expectedCarList, actualCarList);
        Assertions.assertEquals(CarStatus.DRIVING, actualCarList.get(0).getCarStatus());
        Assertions.assertEquals("Mercedes", actualCarList.get(0).getBrand());
        Assertions.assertEquals("Sedan", actualCarList.get(0).getModel());

    }

    @Test
    void calculateNumberOfCarsShouldReturn2(){
        Car car1 = new Car();
        car1.setCarStatus(CarStatus.DRIVING);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");

        Car car2 = new Car();
        car2.setCarStatus(CarStatus.READY_TO_GO);
        car2.setBrand("Fiat");
        car2.setModel("125p");

        List<Car> expectedCarList = new ArrayList<>();
        expectedCarList.add(car1);
        expectedCarList.add(car2);


        when(carRepository.findAll()).thenReturn(expectedCarList);


        Assertions.assertEquals(2, carService.calculateNumberOfCars());

    }

    @Test
    void addCarShouldReturnCreatedCar() {
        //given
        Car car = new Car();
        car.setBrand("Fiat");
        car.setModel("125p");
        
        //when
        when(carRepository.save(any(Car.class))).thenReturn(car);
        Car savedCar = carService.addNewCar(car);

        //then
        assertEquals(car, savedCar);
        assertEquals("Fiat", savedCar.getBrand());
        assertEquals("125p", savedCar.getModel());
        assertNull(savedCar.getCarStatus());
        assertNull(savedCar.getDriver());
        assertNull(savedCar.getCurrentLocalization());
        assertNull(savedCar.getCourses());

    }

    @Test
    void calculateTotalDistanceShouldReturn11(){
        Car car = new Car();
        car.setModel("Sedan");
        car.setBrand("Mercedes");

        Car car2 = new Car();
        car.setModel("125p");
        car.setBrand("Fiat");

        Course course1 = new Course();
        course1.setCar(car);
        course1.setDistance(5.0);
        course1.setCourseStatus(CourseStatus.FINISHED);

        Course course2 = new Course();
        course2.setCar(car2);
        course2.setDistance(6.0);
        course2.setCourseStatus(CourseStatus.GRADED);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        when(courseRepository.findAll()).thenReturn(courses);

        Double result = carService.calculateTotalDistanceOfCars();

        Assertions.assertEquals(11.0, result);
    }



}
