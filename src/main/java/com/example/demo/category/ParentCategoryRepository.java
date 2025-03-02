package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ParentCategoryRepository extends JpaRepository<ParentCategory,Long> {

    boolean existsParentCategoryByName(String name);

}
