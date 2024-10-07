package com.example.nimapfinalproject.services.impl;
import com.example.nimapfinalproject.DTO.CategoryDTO;
import com.example.nimapfinalproject.DTO.ProductDTO;
import com.example.nimapfinalproject.entity.Category;
import com.example.nimapfinalproject.entity.Product;
import com.example.nimapfinalproject.repository.ProductRepository;
import com.example.nimapfinalproject.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductServiceImpl implements ProductService {
        @Autowired
        private ProductRepository proRes;
        @Autowired
        private ModelMapper modelMapper;

        public Page<ProductDTO> getAllProducts(Pageable pageable) {
            Page<Product> entities = proRes.findAll(pageable);
            Page<ProductDTO> dtoPage = entities.map(new Function<Product, ProductDTO>() {
                @Override
                public ProductDTO apply(Product entity) {
                    return convertToDto(entity);
                }
            });
            return dtoPage;
        }
        public ProductDTO createProduct(ProductDTO product) {
            product.setCategory(product.getCategory());
            Product prodObj = modelMapper.map(product, Product.class);
            Product prod = proRes.save(prodObj);
            return convertToDto(prod);
        }
        public ProductDTO getProductById(Long id) {
            return convertToDto(proRes.findById(id).get());
        }
        public ProductDTO updateProduct(Long id, ProductDTO product) {
            Product prodObj = proRes.getReferenceById(id);
            prodObj.setName(product.getName());
            prodObj.setPrice(product.getPrice());
            Product prod = proRes.save(prodObj);
            return convertToDto(prod);
        }
        public void deleteProduct(Long id) {
            proRes.deleteById(id);
        }

    private ProductDTO convertToDto(Product parent) {
        ProductDTO parentDTO = new ProductDTO();
        CategoryDTO childDTO = new CategoryDTO();
        Category child = parent.getCategory();
        if(child != null) {
            childDTO.setName(child.getName());
            childDTO.setId(child.getId());
            parentDTO.setCategory(childDTO);
        }
        parentDTO.setName(parent.getName());
        parentDTO.setPrice(parent.getPrice());
        parentDTO.setId(parent.getId());
        return parentDTO;
    }
}

