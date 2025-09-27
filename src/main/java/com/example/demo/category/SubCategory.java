package com.example.demo.category;


import com.example.demo.product.Product;
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
@Table(name="mes_sub_category")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryId;


    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public SubCategory(String name) {
        this.name = name;
    }
}
