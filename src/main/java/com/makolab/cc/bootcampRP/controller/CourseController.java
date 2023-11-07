package com.makolab.cc.bootcampRP.controller;

import com.makolab.cc.bootcampRP.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    public final CourseService courseService;

    /**
     * Return total fee of all courses
     * @return
     */
    @GetMapping("/totalfee")
    public ResponseEntity<Double> getTotalFee()
    {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.calculateTotalFee());
    }

}
