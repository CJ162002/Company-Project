package com.example.nimapfinalproject.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Product> products;

    // Getters and Setters
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<Product> getProducts() {
            return products;
        }
        public void setProducts(List<Product> products) {
        if(this.products != null){
            this.products.clear();
        } else {
            this.products = new ArrayList<>();
        }
        if (products != null) {
            this.products.addAll(products);
        }
    }
}

