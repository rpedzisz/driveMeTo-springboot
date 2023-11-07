package com.makolab.cc.bootcampRP.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapper.DriverMapper;
import com.makolab.cc.bootcampRP.mapproviderrestclient.RestClient;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.model.User;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.CourseRepository;
import com.makolab.cc.bootcampRP.repository.DriverRepository;
import com.makolab.cc.bootcampRP.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    DriverRepository driverRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    RestClient restClient;

    @Mock
    DriverMapper driverMapper;

    @Test
    void updateLocalizationShouldSetLocalization(){
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setCurrentLocation("51.244365,22.568234");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User updatedUser = userService.updateLocalization(1L, "51.248289,22.566026");

        Assertions.assertEquals("51.248289,22.566026", updatedUser.getCurrentLocation());
    }

    @Test
    void findNearbyDriversShouldReturn1Driver(){
        Car car1 = new Car();
        car1.setCarId(1L);
        car1.setBrand("Mercedes");
        car1.setModel("Sedan");
        car1.setCurrentLocalization("51.195203,22.538486");
        car1.setCarStatus(CarStatus.READY_TO_GO);

        Car car2 = new Car();
        car2.setCarId(2L);
        car2.setBrand("Volksvagen");
        car2.setModel("Golf");
        car2.setCarStatus(CarStatus.READY_TO_GO);

        Driver driver1 = new Driver();
        driver1.setDriverId(1L);
        driver1.setName("Adam");
        Set<DriverLanguages> driver1LanguagesSet = new HashSet<>();
        driver1LanguagesSet.add(DriverLanguages.ENGLISH);
        driver1.setCar(car1);
        driver1.setDriverLanguagesSet(driver1LanguagesSet);

        Driver driver2 = new Driver();
        driver2.setDriverId(2L);
        driver2.setName("Michał");
        Set<DriverLanguages> driver2LanguagesSet = new HashSet<>();
        driver2LanguagesSet.add(DriverLanguages.CHINESE);
        driver2.setCar(car1);
        driver2.setDriverLanguagesSet(driver2LanguagesSet);

        List<Driver> driverList = new ArrayList<>();
        driverList.add(driver1);

        List<Car> carList = new ArrayList<>();
        carList.add(car1);

        User user = new User();
        user.setUserId(1L);
        user.setCurrentLocation("51.203336,22.543313");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(carRepository.findAllByBrandAndCarStatus("Mercedes", CarStatus.READY_TO_GO)).thenReturn(carList);
        when(driverRepository.findAllByDriverLanguagesSetAndCarIn(DriverLanguages.ENGLISH, carList)).thenReturn(driverList);

        DriverDistanceDto dto = new DriverDistanceDto();
        dto.setDriverId(driver1.getDriverId());
        dto.setName(driver1.getName());
        dto.setDriverLanguagesSet(driver1LanguagesSet);
        dto.setDistanceToClient(1.0);
        dto.setCar(car1);
        when(restClient.getDistanceBetween(user.getCurrentLocation(), car1.getCurrentLocalization())).thenReturn(1.0);
        when(driverMapper.objectToDriverDistanceDto(driver1, 1.0)).thenReturn(dto);


        List<DriverDistanceDto> nearbyDrivers = userService
                .findNearbyDrivers(1L, "Mercedes", DriverLanguages.ENGLISH, 1);

        Assertions.assertEquals(1, nearbyDrivers.size());
        Assertions.assertEquals(1L, nearbyDrivers.get(0).getDriverId());
    }

    @Test
    void showRouteListShouldReturn1Route(){
        User user = new User();
        user.setUserId(1L);
        user.setCurrentLocation("51.195203,22.538486");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Route route = new Route();
        route.setId("1");

        List<Route> routes = new ArrayList<>();
        routes.add(route);
        when(restClient.getRoutesListBetween("51.195203,22.538486", "51.203336,22.543313"))
                .thenReturn(routes);

        List<Route> availableRoutes = userService.showAvailableRouteList(1L, "51.203336,22.543313");

        Assertions.assertEquals(1, availableRoutes.size());
        Assertions.assertEquals("1", availableRoutes.get(0).getId());
    }

    @Test
    void orderCourseShouldReturnNewREQUESTEDCourse(){
        User user = new User();
        user.setUserId(1L);
        user.setCurrentLocation("51.195203,22.538486");

        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCurrentLocalization("51.195203,22.538486");
        car.setCarStatus(CarStatus.READY_TO_GO);

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        driver.setCar(car);
        driver.setDriverLanguagesSet(driverLanguagesSet);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        RouteDto chosenRoute = new RouteDto();
        chosenRoute.setRouteId("routeID");
        chosenRoute.setDistance(5.0);
        chosenRoute.setAvgDuration(10.0);

        String destination = "51.247159,22.565895";

        Course newCourse = userService.orderCourse(1L, 1L, destination, chosenRoute);

        Assertions.assertNotNull(newCourse);
        Assertions.assertEquals(CourseStatus.REQUESTED, newCourse.getCourseStatus());
        Assertions.assertEquals(0L, newCourse.getCourseGrade());
        Assertions.assertEquals(driver, newCourse.getDriver());
        Assertions.assertEquals(user, newCourse.getUser());
        Assertions.assertEquals(car, newCourse.getCar());
        Assertions.assertEquals("51.195203,22.538486", newCourse.getStartLocalization());
        Assertions.assertEquals("routeID", newCourse.getChosenRoute());
        Assertions.assertEquals("51.247159,22.565895", newCourse.getDestinationLocalization());
        Assertions.assertEquals(5.0, newCourse.getDistance());
        Assertions.assertEquals(25.0, newCourse.getFee());

    }
    @Test
    void findAllFinishedCoursesShouldReturn2Courses(){
        User user = new User();
        user.setUserId(1L);
        user.setCurrentLocation("51.195203,22.538486");

        Course course1 = new Course();
        course1.setCourseStatus(CourseStatus.FINISHED);
        course1.setUser(user);

        Course course2 = new Course();
        course2.setCourseStatus(CourseStatus.FINISHED);
        course2.setUser(user);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        user.setCourses(courses);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(courseRepository.findAllByUserAndCourseStatus(user, CourseStatus.FINISHED)).thenReturn(courses);

        List<Course> returnedCourses = userService.findAllFinishedCoursesForUser(1L);

        Assertions.assertEquals(2, returnedCourses.size());
        Assertions.assertEquals(courses, returnedCourses);
        Assertions.assertEquals(user, returnedCourses.get(0).getUser());
        Assertions.assertEquals(user, returnedCourses.get(1).getUser());
    }


    @Test
    void gradeFinishedCourseShouldUpdateCourseStatusAndSetGrade(){
        User user = new User();
        user.setUserId(1L);
        user.setCurrentLocation("51.195203,22.538486");

        Car car = new Car();
        car.setCarId(1L);
        car.setBrand("Mercedes");
        car.setModel("Sedan");
        car.setCurrentLocalization("51.195203,22.538486");
        car.setCarStatus(CarStatus.READY_TO_GO);

        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("Adam");
        driver.setAvgGrade(0.0);
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        driver.setCar(car);
        driver.setDriverLanguagesSet(driverLanguagesSet);

        car.setDriver(driver);

        Course course = new Course();
        course.setCourseId(1L);
        course.setCourseStatus(CourseStatus.FINISHED);
        course.setUser(user);
        course.setDriver(driver);
        course.setCar(car);

        List<Course> courses = new ArrayList<>();
        courses.add(course);

        user.setCourses(courses);
        driver.setCourses(courses);
        car.setCourses(courses);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Course gradedCourse = userService.gradeFinishedCourse(1L, 1L, 5L);

        Assertions.assertNotNull(gradedCourse);
        Assertions.assertEquals(CourseStatus.GRADED, gradedCourse.getCourseStatus());
        Assertions.assertEquals(5L, gradedCourse.getCourseGrade());
        Assertions.assertEquals(5.0, gradedCourse.getDriver().getAvgGrade());
    }






}
