package edu.poly.assjava5banhang.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.assjava5banhang.dto.CategoryStatsDTO;
import edu.poly.assjava5banhang.model.OrderDetail;

public interface OrderdetailDAO extends JpaRepository<OrderDetail, Integer> {


        // Trường hợp bạn có entity Category và Product.category là Category
    @Query("""
        SELECT new edu.poly.assjava5banhang.dto.CategoryStatsDTO(
            c.name,
            SUM(od.price * od.quantity),
            SUM(od.quantity),
            MAX(od.price),
            MIN(od.price),
            AVG(od.price)
        )
        FROM OrderDetail od
        JOIN od.product p
        JOIN p.category c
        GROUP BY c.name 
        ORDER BY SUM(od.price * od.quantity) DESC
    """)
    List<CategoryStatsDTO> getCategoryStats();

    @Query("SELECT od From OrderDetail od Where od.orders.id = ?1")
    List<OrderDetail> findHisToryOrderDetails(Integer id);


    
} 