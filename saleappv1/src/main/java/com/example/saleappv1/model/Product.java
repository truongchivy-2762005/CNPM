package com.example.saleappv1.model;

import jakarta.persistence.*;
import lombok.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "products")
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

    public Product() {}

    public Product(Long id, String name, String description, Double price, String image, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

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