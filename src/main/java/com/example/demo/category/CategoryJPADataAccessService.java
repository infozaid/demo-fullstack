package com.example.demo.category;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("category_jpa")
public class CategoryJPADataAccessService implements CategoryDao {

    private final ParentCategoryRepository parentCategoryRepository;

    public CategoryJPADataAccessService(ParentCategoryRepository parentCategoryRepository) {
        this.parentCategoryRepository = parentCategoryRepository;
    }

    @Override
    public void insertParentCategory(ParentCategory parentCategory) {
        parentCategoryRepository.save(parentCategory);
    }

    @Override
    public List<ParentCategory> findAllParentCategory() {
        return parentCategoryRepository.findAll();
    }

    @Override
    public Optional<ParentCategory> finParentCategoryWithId(Long id) {
        return parentCategoryRepository.findById(id);
    }

    @Override
    public boolean existsParentCategoryByName(String name) {
        return parentCategoryRepository.existsParentCategoryByName(name);
    }
}
