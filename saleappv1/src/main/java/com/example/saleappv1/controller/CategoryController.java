package com.example.saleappv1.controller;

import com.example.saleappv1.model.Category;
import com.example.saleappv1.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // 1. Hiển thị danh sách danh mục
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/categories-list";
    }

    // 2. Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/add-category";
    }

    // 3. Xử lý lưu form thêm mới
    @PostMapping("/add")
    public String addCategory(Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/add-category";
        }
        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    // 4. Hiển thị form cập nhật
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID danh mục không hợp lệ: " + id));
        model.addAttribute("category", category);
        return "categories/update-category";
    }

    // 5. Xử lý lưu cập nhật
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result) {
        if (result.hasErrors()) {
            category.setId(id);
            return "categories/update-category";
        }
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    // 6. Xử lý xóa
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }
}