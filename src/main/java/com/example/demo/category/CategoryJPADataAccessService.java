package com.example.demo.category;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("category_jpa")
public class CategoryJPADataAccessService implements CategoryDao {

    private final ParentCategoryRepository parentCategoryRepository;
    private final CategoryRepository categoryRepository;

    public CategoryJPADataAccessService(ParentCategoryRepository parentCategoryRepository, CategoryRepository categoryRepository) {
        this.parentCategoryRepository = parentCategoryRepository;
        this.categoryRepository = categoryRepository;
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
    public Optional<ParentCategory> findParentCategoryWithId(Long id) {
        return parentCategoryRepository.findById(id);
    }

    @Override
    public boolean existsParentCategoryByName(String name) {
        return parentCategoryRepository.existsParentCategoryByName(name);
    }


    @Override
    public void insertCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public boolean existsCategoryWithName(String name) {
        return categoryRepository.existsCategoryByCategoryName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoryByParentCategoryId(Long parentId) {
        return categoryRepository.findCategoryByParentCategoryId(parentId);
    }
}
