package com.example.demo.category;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("category_jpa")
public class CategoryJPADataAccessService implements CategoryDao {

    private final ParentCategoryRepository parentCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public CategoryJPADataAccessService(ParentCategoryRepository parentCategoryRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.parentCategoryRepository = parentCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    // Below are the implementations of ParentCategory
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

    // Below are the implementations of Category

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
    public Optional<Category> findCategoryWithId(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getCategoryByParentCategoryId(Long parentId) {
        return categoryRepository.findCategoryByParentCategoryId(parentId);
    }

    // Below are the implementations of SubCategory

    @Override
    public void insertSubCategory(SubCategory subCategory) {
        subCategoryRepository.save(subCategory);
    }

    @Override
    public List<SubCategory> getAllSubCategory() {
        return subCategoryRepository.findAll();
    }


    @Override
    public boolean existSubCategoryWithName(String name) {
        return subCategoryRepository.existsSubCategoryByName(name);
    }

    @Override
    public List<SubCategory> getAllSubCategoryByCategoryId(Long id) {
        return subCategoryRepository.findSubCategoryByCategoryId(id);
    }


}
