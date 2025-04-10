package edu.poly.assjava5banhang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.assjava5banhang.model.Category;

public interface CategoryDAO  extends JpaRepository<Category,String>{

    @Query("Select c.name From Category c Where c.id = :id")
    String findNameById(String id);

    
} 
