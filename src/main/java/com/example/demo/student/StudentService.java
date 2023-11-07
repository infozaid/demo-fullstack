package com.example.demo.student;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {


    public static long count=0;
    private final StudentRepository studentRepository;


    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public long countEmail(String email){
        count = studentRepository.count(email);
        return count;
    }
    public void addStudent(Student student) {
        if(countEmail(student.getEmail())>0) {
            throw new BadRequestException("Email with Student already exist");
        } else {
            studentRepository.save(student);
        }
    }

    public void deleteStudent(long studentId){
        if(!studentRepository.existsById(studentId)){
            throw new StudentNotFoundException("Student with Id: "+studentId+" does not exist");
        }
        studentRepository.deleteById(studentId);
    }
}
