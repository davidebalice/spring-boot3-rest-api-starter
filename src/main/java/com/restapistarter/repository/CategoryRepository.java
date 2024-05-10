package com.restapistarter.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.restapistarter.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{

    void deleteById(Optional<Category> p);

}
