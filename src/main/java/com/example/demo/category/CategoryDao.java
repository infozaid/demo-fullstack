package com.example.demo.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    void insertParentCategory(ParentCategory parentCategory);
    List<ParentCategory> findAllParentCategory();
    Optional<ParentCategory> findParentCategoryWithId(Long id);
    boolean existsParentCategoryByName(String name);
    List<Category> getCategoryByParentCategoryId(Long parentId);

    void insertCategory(Category category);
    boolean existsCategoryWithName(String name);
    List<Category> getAllCategory();

}
