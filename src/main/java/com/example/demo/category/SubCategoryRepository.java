package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    boolean existsSubCategoryByName(String name);

    @Query("select s from SubCategory s " +
            "left join fetch Category c " +
            "where s.category.categoryId = :categoryId")
    List<SubCategory> findSubCategoryByCategoryId(@Param("categoryId") Long id);
}
