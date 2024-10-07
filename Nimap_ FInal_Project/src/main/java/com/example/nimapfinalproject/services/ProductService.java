package com.example.nimapfinalproject.services;

import com.example.nimapfinalproject.DTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {
    public Page<ProductDTO> getAllProducts(Pageable pageable);
    public ProductDTO createProduct(ProductDTO product);
    public ProductDTO getProductById(Long id);
    public ProductDTO updateProduct(Long id, ProductDTO product);
    public void deleteProduct(Long id);
}


