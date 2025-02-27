package com.example.demo.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    void insertParentCategory(ParentCategory parentCategory);
    List<ParentCategory> findAllParentCategory();
    Optional<ParentCategory> finParentCategoryWithId(Long id);
    boolean existsParentCategoryByName(String name);



}
