package com.makolab.cc.bootcampRP.controller;

import com.makolab.cc.bootcampRP.dto.CourseDto;
import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapper.CourseMapper;
import com.makolab.cc.bootcampRP.mapper.RouteMapper;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.UserRepository;
import com.makolab.cc.bootcampRP.service.CarService;
import com.makolab.cc.bootcampRP.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * User Controller class
 */
@RestController
@Slf4j
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    private final CarService carService;
    private final CourseMapper courseMapper;
    private final RouteMapper routeMapper;

    /**
     * Update current localization for user
     * @param userId
     * @param localization
     * @return
     */
    @PostMapping("/update-localization/{userId}/{localization}")
    public ResponseEntity<String> gradeFinishedCourse(@PathVariable Long userId,
                                                         @PathVariable String localization) {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Current Localisation = " + userService.updateLocalization(userId, localization).getCurrentLocation());

    }

    /**
     * Find List of nearest drivers
     * @param userId
     * @param carBrand
     * @param language
     * @param maxDrivers
     * @return
     */
    @GetMapping("/find-drivers/{userId}/{carBrand}/{language}/{maxDrivers}")
    public ResponseEntity<List<DriverDistanceDto>> findNearbyDrivers(@PathVariable Long userId,
                                         @PathVariable String carBrand,
                                         @PathVariable DriverLanguages language,
                                         @PathVariable int maxDrivers) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findNearbyDrivers(userId, carBrand, language, maxDrivers));
    }

    /**
     * Find 5 best routes between user location and desired destination
     * @param userId
     * @param destination
     * @return
     */
    @GetMapping("/find-routes/{userId}/{destination}")
    public ResponseEntity<List<RouteDto>> findAvailableRoutes(@PathVariable Long userId,
                                                              @PathVariable String destination) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.showAvailableRouteList(userId, destination)
                        .stream()
                        .map(routeMapper::routeToDto)
                        .collect(Collectors.toList()));
    }

    /**
     * Order course with chosenRoute, driver and destination
     * @param userId
     * @param driverId
     * @param destination
     * @param chosenRoute
     * @return
     */
    @PostMapping("/order-course/{userId}/{driverId}/{destination}")
    public ResponseEntity<CourseDto> orderCourse(@PathVariable Long userId,
                                                 @PathVariable Long driverId,
                                                 @PathVariable String destination,
                                                 @RequestBody RouteDto chosenRoute) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseMapper.courseToCourseDto(userService.orderCourse(userId,driverId,destination,chosenRoute)));
    }


    /**
     * List courses with status FINISHED (paid)
     * @param userId
     * @return
     */
    @GetMapping("/list-finished-courses/{userId}")
    public ResponseEntity<List<CourseDto>> listFinishedCourses(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAllFinishedCoursesForUser(userId)
                        .stream()
                        .map(courseMapper::courseToCourseDto)
                        .collect(Collectors.toList()));

    }

    /**
     * Grade FINISHED course in a scale 1-5
     * @param userId
     * @param courseId
     * @param grade
     * @return
     */
    @PostMapping("/grade-course/{userId}/{courseId}/{grade}")
    public ResponseEntity<CourseDto> gradeFinishedCourse(@PathVariable Long userId,
                                                         @PathVariable Long courseId,
                                                         @PathVariable Long grade) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseMapper.courseToCourseDto(userService.gradeFinishedCourse(courseId, userId, grade)));

    }



}
