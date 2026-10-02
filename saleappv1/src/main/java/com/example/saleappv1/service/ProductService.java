package com.example.saleappv1.service;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initData() {
        try {
            ClassPathResource catResource = new ClassPathResource("data/categories.json");
            try (InputStream is = catResource.getInputStream()) {
                categories = objectMapper.readValue(is, new TypeReference<List<Category>>() {});
            }

            ClassPathResource prodResource = new ClassPathResource("data/products.json");
            try (InputStream is = prodResource.getInputStream()) {
                products = objectMapper.readValue(is, new TypeReference<List<Product>>() {});
            }
            System.out.println(">>> Đã nạp thành công: " + categories.size() + " danh mục & " + products.size() + " sản phẩm.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() { return products; }

    public Product getProductById(Integer id) {
        if (id == null) return null;
        return products.stream()
                .filter(p -> p.getId() != null && p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Category> getAllCategories() { return categories; }

    public Category getCategoryById(Integer id) {
        if (id == null) return null;
        return categories.stream()
                .filter(c -> c.getId() != null && c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> filterProducts(Integer categoryId, String keyword, Double fromPrice, Double toPrice) {
        return products.stream()
                .filter(p -> {
                	if (categoryId != null && categoryId > 0) {
                	    if (p.getCategory() == null || p.getCategory().getId() == null || !p.getCategory().getId().equals(Long.valueOf(categoryId))) {
                	        return false;
                	    }
                	}
                    if (keyword != null && !keyword.trim().isEmpty()) {
                        String lowerKw = keyword.trim().toLowerCase();
                        if (p.getName() == null || !p.getName().toLowerCase().contains(lowerKw)) return false;
                    }
                    if (fromPrice != null && fromPrice >= 0) {
                        if (p.getPrice() == null || p.getPrice() < fromPrice) return false;
                    }
                    if (toPrice != null && toPrice >= 0) {
                        if (p.getPrice() == null || p.getPrice() > toPrice) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}