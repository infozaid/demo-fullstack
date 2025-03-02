package com.example.demo.category;


import com.example.demo.category.payload.CategoryRequest;
import com.example.demo.category.payload.ParentCategoryRequest;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    private final ParentCategoryDtoMapper parentCategoryDtoMapper;

    private final CategoryDtoMapper categoryDtoMapper;

    public CategoryService(@Qualifier("category_jpa") CategoryDao categoryDao, ParentCategoryDtoMapper parentCategoryDtoMapper, CategoryDtoMapper categoryDtoMapper) {
        this.categoryDao = categoryDao;
        this.parentCategoryDtoMapper = parentCategoryDtoMapper;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    public List<ParentCategoryDTO> getAllParentCategory(){
       return categoryDao.findAllParentCategory()
               .stream()
               .map(parentCategoryDtoMapper)
               .collect(Collectors.toList());
    }

    public void addParentCategory(ParentCategoryRequest parentCategoryRequest){

        if(categoryDao.existsParentCategoryByName(parentCategoryRequest.name())){
            throw new DuplicateResourceException("Parent category already taken");
        }

        if(parentCategoryRequest.name() == null || parentCategoryRequest.name().isEmpty()){
            throw new IllegalArgumentException("Parent Category Name can not be Null or Empty");
        }

        ParentCategory parentCategory = new ParentCategory(parentCategoryRequest.name());

        categoryDao.insertParentCategory(parentCategory);
    }

    public List<CategoryDTO> getAllCategory(){
      return  categoryDao.getAllCategory()
                .stream()
                .map(categoryDtoMapper)
                .collect(Collectors.toList());
    }

    public void addCategory(CategoryRequest categoryRequest) {

        if(categoryRequest.name()==null || categoryRequest.name().isEmpty() || categoryRequest.id()==null){
            throw new IllegalArgumentException("Category Parameters Can not be Null or empty");
        }

        if(categoryDao.existsCategoryWithName(categoryRequest.name())){
            throw new DuplicateResourceException("Category Name already taken");
        }

        ParentCategory parentCategory = categoryDao.findParentCategoryWithId(categoryRequest.id())
                .orElseThrow(()-> new ResourceNotFoundException("selected parent Id Not Found"));

        Category category = new Category(categoryRequest.name());

        category.setParentCategory(parentCategory);
        categoryDao.insertCategory(category);
    }

    public List<CategoryDTO> getAllCategoryWithParentCategory(Long parentId){
       return categoryDao.getCategoryByParentCategoryId(parentId)
                .stream()
                .map(categoryDtoMapper)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list ->{
                            if(list.isEmpty()){
                                throw new ResourceNotFoundException("Categories Not Found with Id"+parentId);
                            }
                            return list;
                        }
                ));
    }
}
