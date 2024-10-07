package com.example.nimapfinalproject.controller;
import com.example.nimapfinalproject.DTO.CategoryDTO;
import com.example.nimapfinalproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryControl {
        @Autowired
        private CategoryService categoryService;
        @GetMapping
        public Page<CategoryDTO> getAllCategories(@PageableDefault(page = 0, size = 10)Pageable pageable) {
            return categoryService.getAllCategories(pageable);
        }
        @PostMapping
        public CategoryDTO createCategory(@RequestBody CategoryDTO category) {
            return categoryService.createCategory(category);
        }
        @GetMapping("/{id}")
        public CategoryDTO getCategoryById(@PathVariable Long id) {
            return categoryService.getCategoryById(id);
        }
        @PutMapping("/{id}")
        public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO category) {
            return categoryService.updateCategory(id, category);
        }
        @DeleteMapping("/{id}")
        public void deleteCategory(@PathVariable Long id) {
            categoryService.deleteCategory(id);
        }
}
