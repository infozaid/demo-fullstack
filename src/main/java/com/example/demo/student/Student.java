package com.example.demo.student;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class Student {

    @Id
    @SequenceGenerator(name="student_sequence",
    sequenceName = "student_sequence",
    allocationSize = 1)
    @GeneratedValue(generator = "student_sequence",
            strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
