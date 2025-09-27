package com.example.demo.category;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Category> categories;

    public ParentCategory(String name) {
        this.name = name;
    }
}
