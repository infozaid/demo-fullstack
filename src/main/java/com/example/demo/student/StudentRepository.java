package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("select count(st) from Student st where st.email =:email")
    public long count(@Param("email") String email);

}
