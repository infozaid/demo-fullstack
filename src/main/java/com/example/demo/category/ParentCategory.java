package com.example.demo.category;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mes_parent_category")
public class ParentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentCategoryId;

    private String name;

    @OneToMany(mappedBy = "mes_parent_category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Category> categories;

}
