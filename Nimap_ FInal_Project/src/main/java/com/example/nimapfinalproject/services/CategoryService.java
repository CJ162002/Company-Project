package com.example.nimapfinalproject.services;
import com.example.nimapfinalproject.DTO.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    public Page<CategoryDTO> getAllCategories(Pageable pageable);
    public CategoryDTO createCategory(CategoryDTO category);
    public CategoryDTO getCategoryById(Long id);
    public CategoryDTO updateCategory(Long id, CategoryDTO category);
    public void deleteCategory(Long id);
}
