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
@Table(name="mes_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name="parent_category_id", nullable = false)
    private ParentCategory parentCategory;

    @OneToMany(mappedBy = "mes_category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<SubCategory> subCategories;

}
