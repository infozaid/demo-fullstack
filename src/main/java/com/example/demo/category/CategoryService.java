package com.example.demo.category;


import com.example.demo.category.payload.CategoryRequest;
import com.example.demo.category.payload.ParentCategoryRequest;
import com.example.demo.category.payload.SubCategoryRequest;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    private final ParentCategoryDtoMapper parentCategoryDtoMapper;

    private final CategoryDtoMapper categoryDtoMapper;

    private final SubCategoryDtoMapper subCategoryDtoMapper;

    public CategoryService(@Qualifier("category_jpa") CategoryDao categoryDao, ParentCategoryDtoMapper parentCategoryDtoMapper, CategoryDtoMapper categoryDtoMapper, SubCategoryDtoMapper subCategoryDtoMapper) {
        this.categoryDao = categoryDao;
        this.parentCategoryDtoMapper = parentCategoryDtoMapper;
        this.categoryDtoMapper = categoryDtoMapper;
        this.subCategoryDtoMapper = subCategoryDtoMapper;
    }

    // Below are the implementations of ParentCategory

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

    // Below are the implementations of Category

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
                .orElseThrow(()-> new ResourceNotFoundException("selected parent Id Not Found with id: "+categoryRequest.id()));

        Category category = new Category(categoryRequest.name());

        category.setParentCategory(parentCategory);
        categoryDao.insertCategory(category);
    }

    public List<CategoryDTO> getAllCategoryWithParentCategory(Long parentCategoryId){
       return categoryDao.getCategoryByParentCategoryId(parentCategoryId)
                .stream()
                .map(categoryDtoMapper)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list ->{
                            if(list.isEmpty()){
                                throw new ResourceNotFoundException("Categories Not Found with Id: "+parentCategoryId);
                            }
                            return list;
                        }
                ));
    }

    // Below are the implementations of SubCategory

    public List<SubCategoryDTO> getAllSubCategory(){
        return categoryDao.getAllSubCategory()
                .stream()
                .map(subCategoryDtoMapper)
                .toList();
    }

    public void addSubCategory(SubCategoryRequest subcategoryRequest) {
        if(subcategoryRequest.name()==null || subcategoryRequest.name().isEmpty() || subcategoryRequest.id()==null){
            throw new IllegalArgumentException("SubCategory Parameters Can not be null or empty");
        }
        if(categoryDao.existSubCategoryWithName(subcategoryRequest.name())){
            throw new DuplicateResourceException("SubCategory Name already taken "+subcategoryRequest.name());
        }

        Category category = categoryDao.findCategoryWithId(subcategoryRequest.id())
                .orElseThrow(()->new ResourceNotFoundException("Selected category Id Not found with id: "+subcategoryRequest.id()));

        SubCategory subCategory = new SubCategory(subcategoryRequest.name());
        subCategory.setCategory(category);
        categoryDao.insertSubCategory(subCategory);
    }

    public List<SubCategoryDTO> getAllSubCategoryWithCategoryId(Long categoryId) {
        return categoryDao.getAllSubCategoryByCategoryId(categoryId)
                .stream()
                .map(subCategoryDtoMapper)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                            if (list.isEmpty()) {
                                throw new ResourceNotFoundException("SubCategories Not Found with categoryId: " + categoryId);
                            }
                            return list;
                        }
                ));
    }


}
