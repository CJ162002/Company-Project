package com.example.nimapfinalproject.controller;
import com.example.nimapfinalproject.DTO.ProductDTO;
import com.example.nimapfinalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductControl {
        @Autowired
        private ProductService productService;
        @GetMapping
        public Page<ProductDTO> getAllProducts(@PageableDefault(page = 0, size = 10)Pageable pageable) {
            return productService.getAllProducts(pageable);
        }
        @PostMapping
        public ProductDTO createProduct(@RequestBody ProductDTO product) {
            return productService.createProduct(product);
        }
        @GetMapping("/{id}")
        public ProductDTO getProductById(@PathVariable Long id) {
            return productService.getProductById(id);
        }
        @PutMapping("/{id}")
        public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
            return productService.updateProduct(id, product);
        }
        @DeleteMapping("/{id}")
        public void deleteProduct(@PathVariable Long id) {
            productService.deleteProduct(id);
        }
}
