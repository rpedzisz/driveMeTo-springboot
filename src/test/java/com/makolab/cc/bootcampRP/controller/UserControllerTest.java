package com.makolab.cc.bootcampRP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.dto.RouteDto;
import com.makolab.cc.bootcampRP.mapper.DriverMapper;
import com.makolab.cc.bootcampRP.mapper.RouteMapper;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Section;
import com.makolab.cc.bootcampRP.mapproviderrestclient.TravelSummary;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.CourseStatus;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.model.User;
import com.makolab.cc.bootcampRP.service.DriverService;
import com.makolab.cc.bootcampRP.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Slf4j
@TestPropertySource(locations = "classpath:tests.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RouteMapper routeMapper;

    @Test
    void updateLocalizationShouldReturnOK() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setCurrentLocation("51.195142,22.538410");
        when(userService.updateLocalization(1L, "51.195142,22.538410")).thenReturn(user);

        MvcResult mvcResult = mockMvc
                .perform(post("/users/update-localization/{userId}/{localization}",
                        1L, "51.195142,22.538410"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "Current Localisation = 51.195142,22.538410";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    void findDriversShouldReturnOK() throws Exception {
        DriverDistanceDto driverDistanceDto = new DriverDistanceDto();
        driverDistanceDto.setDriverId(1L);
        driverDistanceDto.setDistanceToClient(5.0);
        driverDistanceDto.setName("Adam");
        driverDistanceDto.setAvgGrade(3.0);
        Set<DriverLanguages> driverLanguagesSet = new HashSet<>();
        driverLanguagesSet.add(DriverLanguages.ENGLISH);
        Car car = new Car();
        car.setCarId(1L);
        car.setCarStatus(CarStatus.READY_TO_GO);
        car.setBrand("Fiat");
        car.setModel("125p");
        driverDistanceDto.setCar(car);
        List<DriverDistanceDto> driverDistanceDtoList = new ArrayList<>();
        driverDistanceDtoList.add(driverDistanceDto);

        when(userService.findNearbyDrivers(1L, "Fiat", DriverLanguages.ENGLISH ,1))
                .thenReturn(driverDistanceDtoList);


        MvcResult mvcResult = mockMvc
                .perform(get("/users/find-drivers/{userId}/{brand}/{language}/{maxDrivers}",
                        1L, "Fiat", "ENGLISH", 1))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"driverId\":1,\"name\":\"Adam\"," +
                "\"car\":{\"carId\":1,\"brand\":\"Fiat\",\"model\":\"125p\",\"currentLocalization\":null,\"carStatus\":\"READY_TO_GO\"}," +
                "\"avgGrade\":3.0,\"distanceToClient\":5.0}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);
    }

    @Test
    void findRoutesShouldReturnOK() throws Exception {

        Route route = new Route();
        route.setId("routeId");
        Section section = new Section();
        TravelSummary travelSummary = new TravelSummary();
        travelSummary.setDuration(600L);
        travelSummary.setLength(5000L);
        section.setTravelSummary(travelSummary);
        List<Section> sections = new ArrayList<>();
        sections.add(section);
        route.setSections(sections);
        List<Route> routes = new ArrayList<>();
        routes.add(route);
        when(userService.showAvailableRouteList(1L, "51.250970,22.574776"))
                .thenReturn(routes);

        MvcResult mvcResult = mockMvc
                .perform(get("/users/find-routes/{userId}/{destination}/",
                        1L, "51.250970,22.574776"))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"routeId\":\"routeId\",\"distance\":5.0,\"avgDuration\":600.0}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);


    }

    @Test
    void orderCourseShouldReturnOK() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setCurrentLocation("51.255041,22.545090");
        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("michał");
        Car car = new Car();
        car.setCarId(1L);
        car.setDriver(driver);
        car.setBrand("Mercedes");
        car.setBrand("Sedan");
        driver.setCar(car);

        RouteDto chosenRoute = new RouteDto();
        chosenRoute.setRouteId("routeId");
        chosenRoute.setDistance(5.0);
        chosenRoute.setAvgDuration(10.0);

        Course course = new Course();
        course.setCourseStatus(CourseStatus.REQUESTED);
        course.setCourseGrade(0L);
        course.setCar(car);
        course.setDriver(driver);
        course.setUser(user);
        course.setFee(25.0);
        course.setDistance(chosenRoute.getDistance());
        course.setStartLocalization(user.getCurrentLocation());
        course.setChosenRoute(chosenRoute.getRouteId());
        course.setDestinationLocalization("51.250970,22.574776");


        when(userService.orderCourse(1L, 1L, "51.250970,22.574776", chosenRoute))
                .thenReturn(course);

        MvcResult mvcResult = mockMvc
                .perform(post("/users/order-course/{userId}/{driverId}/{destination}",
                        1L, 1L, "51.250970,22.574776")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chosenRoute)))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "{\"courseStatus\":\"REQUESTED\"," +
                "\"startLocalization\":\"51.255041,22.545090\"," +
                "\"chosenRoute\":\"routeId\"," +
                "\"destinationLocalization\":\"51.250970,22.574776\"," +
                "\"distance\":5.0," +
                "\"fee\":25.0," +
                "\"courseGrade\":0," +
                "\"carId\":1,\"driverId\":1,\"userId\":1}";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);



    }

    @Test
    void gradeCourseShouldReturnOK() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setCurrentLocation("51.255041,22.545090");
        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("michał");
        Car car = new Car();
        car.setCarId(1L);
        car.setDriver(driver);
        car.setBrand("Mercedes");
        car.setBrand("Sedan");
        driver.setCar(car);

        RouteDto chosenRoute = new RouteDto();
        chosenRoute.setRouteId("routeId");
        chosenRoute.setDistance(5.0);
        chosenRoute.setAvgDuration(10.0);

        Course gradedCourse = new Course();
        gradedCourse.setCourseStatus(CourseStatus.GRADED);
        gradedCourse.setCourseGrade(5L);
        gradedCourse.setCar(car);
        gradedCourse.setDriver(driver);
        gradedCourse.setUser(user);
        gradedCourse.setFee(25.0);
        gradedCourse.setDistance(chosenRoute.getDistance());
        gradedCourse.setStartLocalization(user.getCurrentLocation());
        gradedCourse.setChosenRoute(chosenRoute.getRouteId());
        gradedCourse.setDestinationLocalization("51.250970,22.574776");

        when(userService.gradeFinishedCourse(1L, 1L, 5L)).thenReturn(gradedCourse);

        MvcResult mvcResult = mockMvc
                .perform(post("/users/grade-course/{userId}/{courseId}/{grade}",
                        1L, 1L, 5L))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "{\"courseStatus\":\"GRADED\"," +
                "\"startLocalization\":\"51.255041,22.545090\"," +
                "\"chosenRoute\":\"routeId\"," +
                "\"destinationLocalization\":\"51.250970,22.574776\"," +
                "\"distance\":5.0,\"fee\":25.0,\"courseGrade\":5," +
                "\"carId\":1,\"driverId\":1,\"userId\":1}";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);

    }

    @Test
    void listFinishedCoursesShouldReturnOK() throws Exception {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("adam");
        user.setCurrentLocation("51.255041,22.545090");


        Driver driver = new Driver();
        driver.setDriverId(1L);
        driver.setName("michał");
        Car car = new Car();
        car.setCarId(1L);
        car.setDriver(driver);
        car.setBrand("Mercedes");
        car.setBrand("Sedan");
        driver.setCar(car);

        RouteDto chosenRoute = new RouteDto();
        chosenRoute.setRouteId("routeId");
        chosenRoute.setDistance(5.0);
        chosenRoute.setAvgDuration(10.0);


        Course course1 = new Course();
        course1.setCourseId(1L);
        course1.setCourseStatus(CourseStatus.FINISHED);
        course1.setCar(car);
        course1.setDriver(driver);
        course1.setUser(user);
        course1.setFee(25.0);
        course1.setDistance(chosenRoute.getDistance());
        course1.setStartLocalization(user.getCurrentLocation());
        course1.setChosenRoute(chosenRoute.getRouteId());
        course1.setDestinationLocalization("51.250970,22.574776");

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        when(userService.findAllFinishedCoursesForUser(1L)).thenReturn(courses);

        MvcResult mvcResult = mockMvc
                .perform(get("/users/list-finished-courses/{userId}",
                        1L))
                .andExpect(status().isOk())
                .andReturn();
        String expectedBody = "[{\"courseId\":1,\"courseStatus\":\"FINISHED\",\"startLocalization\":\"51.255041,22.545090\"," +
                "\"chosenRoute\":\"routeId\",\"destinationLocalization\":\"51.250970,22.574776\"," +
                "\"distance\":5.0,\"fee\":25.0,\"carId\":1,\"driverId\":1,\"userId\":1}]";
        String actualBody = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedBody, actualBody);



    }


}
