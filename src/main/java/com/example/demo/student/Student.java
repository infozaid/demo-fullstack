package com.example.demo.student;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Student {

    private long id;
    private String name;
    private String email;
    private Gender gender;
}
