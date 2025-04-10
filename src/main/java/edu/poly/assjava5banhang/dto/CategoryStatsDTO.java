package edu.poly.assjava5banhang.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryStatsDTO {

    private String categoryName;    // Tên loại hàng
    private double totalRevenue;    // Tổng doanh thu
    private long totalQuantity;     // Tổng số lượng
    private double maxPrice;        // Giá cao nhất
    private double minPrice;        // Giá thấp nhất
    private double avgPrice;        // Giá trung bình

    public CategoryStatsDTO(String categoryName, double totalRevenue, long totalQuantity,
                            double maxPrice, double minPrice, double avgPrice) {
        this.categoryName = categoryName;
        this.totalRevenue = totalRevenue;
        this.totalQuantity = totalQuantity;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.avgPrice = avgPrice;
    }
    
}
