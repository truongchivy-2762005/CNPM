package com.example.saleappv1.model;

import jakarta.persistence.*;
import lombok.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Giữ lại hàm này để Thymeleaf hiển thị giá tiền VNĐ đẹp mắt
    public String getFormattedPrice() {
        if (price == null) return "0 đ";
        DecimalFormat formatter = new DecimalFormat("###,###,### đ");
        return formatter.format(price);
    }
    public Long getCategoryId() {
        return this.category != null ? this.category.getId() : null;
    }
}