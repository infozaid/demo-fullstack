package com.example.demo.student;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable=false,unique = true)
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Gender gender;
}
