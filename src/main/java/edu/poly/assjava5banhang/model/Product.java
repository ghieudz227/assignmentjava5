package edu.poly.assjava5banhang.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;

import lombok.Data;
@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;


    private String image;


    @Min(value = 1, message = "Giá phải lớn hơn 0")
    private Double price;

    
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate = new Date();


    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> oderDetails;

}
