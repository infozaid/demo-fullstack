package com.example.demo.product;


import com.example.demo.brand.Brand;
import com.example.demo.category.Category;
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
@Table(name = "mes_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq",
            sequenceName = "product_id_seq",
            initialValue = 101,
            allocationSize = 1)
    private Long productId;

    private String productName;

    private String productDescription;

    private Double avgRating;

    private Integer rating;

    private String imageUrl;

    private String location;

    private Integer finalPrice;

    private Integer originalPrice;

    private Integer quantity;

    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "mes_product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductReviews> reviews;

    @OneToMany(mappedBy = "mes_product",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductAttribute> productAttributes;



}
