package com.pranjal.repository;

import com.pranjal.enitity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIsActiveTrue();

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsDeletedFalse();

    Boolean existsByNameIgnoreCase(String categoryName);
}
