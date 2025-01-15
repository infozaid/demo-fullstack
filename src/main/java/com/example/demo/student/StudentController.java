package com.example.demo.student;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student>  getAllStudents(){

        /*List<Student> studentList = Arrays.asList(
                new Student(1L,
                        "Usman",
                        "usman@gmail.com",
                        Gender.MALE),

        new Student(2L,
                "Salman",
                "salman@gmail.com",
                Gender.MALE)
        );

        return studentList;*/

        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@Valid @RequestBody Student student){
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") long studentId){
        studentService.deleteStudent(studentId);
    }
}
