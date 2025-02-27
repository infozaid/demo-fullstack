package com.example.demo.category;


import com.example.demo.category.payload.ParentCategoryRequest;
import com.example.demo.exception.DuplicateResourceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    private final ParentCategoryDtoMapper parentCategoryDtoMapper;

    public CategoryService(@Qualifier("category_jpa") CategoryDao categoryDao, ParentCategoryDtoMapper parentCategoryDtoMapper) {
        this.categoryDao = categoryDao;
        this.parentCategoryDtoMapper = parentCategoryDtoMapper;
    }

    public List<ParentCategory> getAllParentCategory(){
       return categoryDao.findAllParentCategory();
    }

    public void addParentCategory(ParentCategoryRequest parentCategoryRequest){

        if(categoryDao.existsParentCategoryByName(parentCategoryRequest.name())){
            throw new DuplicateResourceException("Parent category already taken");
        }

        if(parentCategoryRequest==null ||parentCategoryRequest.name()==null || parentCategoryRequest.name().isEmpty()){
            throw new IllegalArgumentException("Parent Category Name can not be Null or Empty");
        }

        ParentCategory parentCategory = new ParentCategory(parentCategoryRequest.name());

        categoryDao.insertParentCategory(parentCategory);
    }
}
