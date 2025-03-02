package com.example.demo.category;

public record CategoryDTO(
        Long id,
        String name,
        Long parentCategoryId
) {
}
