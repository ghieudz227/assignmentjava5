package edu.poly.assjava5banhang.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    @Id
    String id;

    String name;

    @OneToMany(mappedBy = "category")
    List<Product> products;
    

    
}
