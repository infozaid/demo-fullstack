package com.example.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @GetMapping
    public List<Student>  getAllStudents(){
        List<Student> studentList = Arrays.asList(
                new Student(1L,
                        "Usman",
                        "usman@gmail.com",
                        Gender.MALE),

        new Student(1L,
                "Salman",
                "salman@gmail.com",
                Gender.MALE)
        );

        return studentList;

    }
}
