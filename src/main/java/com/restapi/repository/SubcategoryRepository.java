package com.restapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restapi.model.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory,Integer>{
    List<Subcategory> findByCategoryId(int categoryId);
    void deleteById(Optional<Subcategory> p);

     @Query("SELECT s FROM Subcategory s " +
            "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " )
    List<Subcategory> searchSubcategories(@Param("keyword") String keyword);

}
