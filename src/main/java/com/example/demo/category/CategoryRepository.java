package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsCategoryByCategoryName(String name);

    @Query("select c from Category c left join fetch c.parentCategory where c.parentCategory.parentCategoryId=:parentCategoryId")
    List<Category> findCategoryByParentCategoryId(@Param("parentCategoryId") Long id);
}
