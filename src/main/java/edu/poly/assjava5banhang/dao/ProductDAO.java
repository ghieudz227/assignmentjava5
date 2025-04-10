package edu.poly.assjava5banhang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.poly.assjava5banhang.model.Product;

public interface ProductDAO extends JpaRepository<Product,Integer> {

    @Query(value = "Select Top 8 * from Products Where available = 1 order by createdate desc, id desc",nativeQuery=true)
    List<Product> findTop8NewestProductsSQL();

    @Query("Select p From Product p where p.category.id = :id And p.available = true order by p.createDate DESC")
    List<Product> findProDuctByCategory(String id);

    @Query("Select p From Product p where p.category.id ='DT' order by p.createDate desc, p.id desc")
    List<Product> findProDuctByCategoryDienThoai();

    @Query("Select p From Product p where p.category.id ='LT' order by p.createDate desc, p.id desc")
    List<Product> findProDuctByCategoryLaptop();

    @Query(value = "SELECT p.* FROM products p " +
                "JOIN order_details od ON p.id = od.productid " +
                "GROUP BY p.id, p.name, p.image, p.price, p.description, p.createdate, p.available, p.categoryid " +
                "ORDER BY SUM(od.quantity) DESC " 
                , nativeQuery = true)
    List<Product> findTop8BestSellingProducts();

    @Query("SELECT p FROM Product p WHERE p.category.id = :category AND p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceAndCategory(@Param("category") String category, 
                                     @Param("minPrice") Double minPrice, 
                                     @Param("maxPrice") Double maxPrice);


    @Query("Select p From Product p Where p.name LIKE ?1")                                 
    List<Product> findByName(String name);



}





    
    
