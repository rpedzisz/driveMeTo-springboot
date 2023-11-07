package com.makolab.cc.bootcampRP.service;

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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User Service class
 */
@Service
@Data
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final CourseRepository courseRepository;
    private final DriverMapper driverMapper;

    Double pricePerKM = 5.0;

    private final RestClient restClient;

    /**
     * Update current user localization
     * @param userId
     * @param localization
     * @return
     */
    public User updateLocalization(Long userId, String localization){
        User user = userRepository.findById(userId).orElse(null);
        if(user!= null && StringUtils.isNotBlank(localization)){
            user.setCurrentLocation(localization);
            return user;
        }
        return null;
    }

    /**
     * Find x closest drivers to user with criteria - carbrand, driverlanguage
     * @param userId
     * @param carBrand
     * @param driverLanguage
     * @param maxDrivers
     * @return
     */
    public List<DriverDistanceDto> findNearbyDrivers(Long userId, String carBrand, DriverLanguages driverLanguage, int maxDrivers){
        User user = userRepository.findById(userId).orElse(null);


        String currentLocalization = user.getCurrentLocation();

        List<Car> carList = carRepository.findAllByBrandAndCarStatus(carBrand, CarStatus.READY_TO_GO);

        List<Driver> drivers = driverRepository.findAllByDriverLanguagesSetAndCarIn(driverLanguage, carList);


        ArrayList<DriverDistanceDto> closestDrivers = new ArrayList<>();

        Double distanceToClient = 0.0;
        for(Driver driver: drivers){
            distanceToClient = restClient.getDistanceBetween(currentLocalization, driver.getCar().getCurrentLocalization());
                closestDrivers.add(driverMapper.objectToDriverDistanceDto(driver, distanceToClient));

        }
        Comparator<DriverDistanceDto> driverDistanceComparator
                = Comparator.comparing(DriverDistanceDto::getDistanceToClient);
        Collections.sort(closestDrivers, driverDistanceComparator);


        return closestDrivers.stream().limit(maxDrivers).collect(Collectors.toList());
    }

    /**
     * List up to 5 best routes between user localization and desired destination
     * @param userId
     * @param destination
     * @return
     */
    public List<Route> showAvailableRouteList(Long userId, String destination){
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            return restClient.getRoutesListBetween(user.getCurrentLocation(),destination);
        }
        return new ArrayList<>();
    }

    /**
     * Order course for user with desired car(driver), chosenRoute and destination
     * @param userId
     * @param driverId
     * @param destination
     * @param chosenRoute
     * @return
     */
    public Course orderCourse(Long userId, Long driverId, String destination, RouteDto chosenRoute){
        User user = userRepository.findById(userId).orElse(null);
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if(user != null && driver != null){
            Course course = new Course();
            course.setCourseStatus(CourseStatus.REQUESTED);
            course.setCourseGrade(0L);
            course.setDriver(driver);
            course.setUser(user);
            course.setCar(driver.getCar());
            course.setStartLocalization(user.getCurrentLocation());
            course.setDestinationLocalization(destination);
            course.setChosenRoute(chosenRoute.getRouteId());
            course.setDistance(chosenRoute.getDistance());
            course.setFee(chosenRoute.getDistance()*pricePerKM);

            courseRepository.save(course);
            return course;
        }
        return null;
    }

    /**
     * Return all finished (and paid) courses for user
     * @param userId
     * @return
     */
    public List<Course> findAllFinishedCoursesForUser(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            return courseRepository.findAllByUserAndCourseStatus(user, CourseStatus.FINISHED);
        }
        return new ArrayList<>();
    }

    /**
     * Allows user to grade course that has been finished
     * @param courseId
     * @param userId
     * @param grade
     * @return
     */
    public Course gradeFinishedCourse(Long courseId,Long userId, Long grade){
        Course course = courseRepository.findById(courseId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(course != null
                && user != null
                && course.getCourseStatus() == CourseStatus.FINISHED
                && user.getCourses().contains(course)
                &&(grade >= 1 && grade <= 5)){
            course.setCourseGrade(grade);
            course.setCourseStatus(CourseStatus.GRADED);
            Driver driver = course.getDriver();
            driver.calculateAvgGrade();
            driverRepository.save(driver);
            return course;
        }
        return null;
    }




}
