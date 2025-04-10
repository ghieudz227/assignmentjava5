package edu.poly.assjava5banhang.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.poly.assjava5banhang.dto.VipCustomerDTO;
import edu.poly.assjava5banhang.model.Account;
import edu.poly.assjava5banhang.model.Order;

public interface OrderDAO extends JpaRepository<Order, Long> {

   

    @Query(value = "SELECT o.id AS OrderID, o.address, o.createdate, " +
               "p.name AS ProductName, od.quantity, od.price " +
               "FROM orders o " +
               "JOIN order_details od ON o.id = od.orderid " +
               "JOIN products p ON od.productid = p.id " +
               "WHERE o.username = :username " +
               "ORDER BY o.createdate DESC", 
                 nativeQuery = true)
    List<Object[]> findOrdersByUsername(@Param("username") String username);

    @Query("""
        SELECT new edu.poly.assjava5banhang.dto.VipCustomerDTO(
            a.fullname,
            SUM(od.price * od.quantity),
            MIN(o.createDate),
            MAX(o.createDate)
        )
        FROM Order o
        JOIN o.account a
        JOIN o.orderDetails od
        GROUP BY a.fullname
        ORDER BY SUM(od.price * od.quantity) DESC
    """)
    List<VipCustomerDTO> getVipCustomer();

    List<Order> findByAccount(Account account);

}
