package com.example.nimapfinalproject.services.impl;
import com.example.nimapfinalproject.DTO.CategoryDTO;
import com.example.nimapfinalproject.DTO.ProductDTO;
import com.example.nimapfinalproject.entity.Category;
import com.example.nimapfinalproject.entity.Product;
import com.example.nimapfinalproject.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.nimapfinalproject.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
        @Autowired
        private CategoryRepository catRes;
        @Autowired
        private ModelMapper modelMapper;

        public Page<CategoryDTO> getAllCategories(Pageable pageable) {
            Page<Category> entities = catRes.findAll(pageable);
            Page<CategoryDTO> dtoPage = entities.map(new Function<Category, CategoryDTO>() {
                @Override
                public CategoryDTO apply(Category entity) {

                    return convertToDto(entity);
                }
            });
            return dtoPage;
        }
        public CategoryDTO createCategory(CategoryDTO category) {
            category.getProducts().forEach(product->product.setCategory(category));
            Category catObj = modelMapper.map(category, Category.class);
            Category cat = catRes.save(catObj);
            return convertToDto(cat);
        }
        public CategoryDTO getCategoryById(Long id) {
            return convertToDto(catRes.findById(id).get());
        }
        public CategoryDTO updateCategory(Long id, CategoryDTO category) {
            Category catObj = catRes.getReferenceById(id);
            catObj.setName(category.getName());
            catObj.setProducts(category.getProducts().stream()
                    .map(productDTO -> {
                        // Convert ProductDTO to Product and set category
                        Product product = modelMapper.map(productDTO, Product.class);
                        product.setCategory(catObj);
                        return product;
        }).collect(Collectors.toList()));
            Category updatedCategory = catRes.save(catObj);
            return convertToDto(updatedCategory);
        }
        public void deleteCategory(Long id) {
            catRes.deleteById(id);
        }

    private CategoryDTO convertToDto(Category parent) {
        CategoryDTO parentDTO = new CategoryDTO();

        List<ProductDTO> childDTOs = parent.getProducts().stream()
                .map(child -> {
                    ProductDTO childDTO = new ProductDTO();
                    childDTO.setId(child.getId());
                    childDTO.setName(child.getName());
                    childDTO.setPrice(child.getPrice());
                    return childDTO;  // Skip setting the parent field to break the cycle
                }).collect(Collectors.toList());
        parentDTO.setName(parent.getName());
        parentDTO.setProducts(childDTOs);
        parentDTO.setId(parent.getId());
        return parentDTO;
    }
}
