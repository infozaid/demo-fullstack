package com.example.demo.category;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ParentCategoryDtoMapper implements Function<ParentCategory,ParentCategoryDTO> {
    @Override
    public ParentCategoryDTO apply(ParentCategory parentCategory) {
        return new ParentCategoryDTO(
                parentCategory.getParentCategoryId(),
                parentCategory.getName()
        );
    }
}
