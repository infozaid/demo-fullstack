package com.example.demo.category;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubCategoryDtoMapper implements Function<SubCategory,SubCategoryDTO> {
    @Override
    public SubCategoryDTO apply(SubCategory subCategory) {
        return new SubCategoryDTO(
                subCategory.getSubCategoryId(),
                subCategory.getName(),
                subCategory.getCategory().getCategoryId()
        );
    }
}
