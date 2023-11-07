package com.makolab.cc.bootcampRP;


import com.makolab.cc.bootcampRP.dto.DriverDistanceDto;
import com.makolab.cc.bootcampRP.mapproviderrestclient.RestClient;
import com.makolab.cc.bootcampRP.mapproviderrestclient.Route;
import com.makolab.cc.bootcampRP.model.Car;
import com.makolab.cc.bootcampRP.model.CarStatus;
import com.makolab.cc.bootcampRP.model.Course;
import com.makolab.cc.bootcampRP.model.Driver;
import com.makolab.cc.bootcampRP.model.DriverLanguages;
import com.makolab.cc.bootcampRP.model.User;
import com.makolab.cc.bootcampRP.repository.CarRepository;
import com.makolab.cc.bootcampRP.repository.UserRepository;
import com.makolab.cc.bootcampRP.service.CarService;
import com.makolab.cc.bootcampRP.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Spring application
 * @author rafal.pedzisz
 */
@SpringBootApplication
@RestController
@Slf4j
public class Application {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(Application.class, args);

	}


}
