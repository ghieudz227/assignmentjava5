package edu.poly.assjava5banhang.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VipCustomerDTO {

    private String customerName;
    private Double totalSpent;
    private Date firstOrderDate;
    private Date lastOrderDate;

    public VipCustomerDTO(String customerName, Double totalSpent, Date firstOrderDate, Date lastOrderDate) {
        this.customerName = customerName;
        this.totalSpent = totalSpent;
        this.firstOrderDate = firstOrderDate;
        this.lastOrderDate = lastOrderDate;
    }


    
}
