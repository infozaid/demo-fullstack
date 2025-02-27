package com.example.demo.category;


import com.example.demo.category.payload.ParentCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ParentCategory> getParenCategoryList(){
        return categoryService.getAllParentCategory();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody ParentCategoryRequest parentCategoryRequest){
        categoryService.addParentCategory(parentCategoryRequest);
        return ResponseEntity.ok().body(parentCategoryRequest);
    }


}
