package com.example.demo.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    // parent category operations
    void insertParentCategory(ParentCategory parentCategory);
    List<ParentCategory> findAllParentCategory();
    Optional<ParentCategory> findParentCategoryWithId(Long id);
    boolean existsParentCategoryByName(String name);

    // Category operations
    List<Category> getCategoryByParentCategoryId(Long parentId);
    void insertCategory(Category category);
    boolean existsCategoryWithName(String name);
    List<Category> getAllCategory();
    Optional<Category> findCategoryWithId(Long id);

    // Sub Category operations
    void insertSubCategory(SubCategory subCategory);
    List<SubCategory> getAllSubCategory();
    boolean existSubCategoryWithName(String name);
    List<SubCategory> getAllSubCategoryByCategoryId(Long id);

}
