package com.xiaoyuan.schoolcircle.service;

import java.math.BigDecimal;
import com.xiaoyuan.schoolcircle.entity.Product;
import com.xiaoyuan.schoolcircle.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public boolean addProduct(Product product) {
        if (product.getTitle() == null || product.getTitle().isEmpty()) {
            return false;
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (product.getStatus() == null) {
            product.setStatus(1);
        }
        return productMapper.insert(product) > 0;
    }

    public Product getProductById(Integer id) {
        return productMapper.findById(id);
    }

    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }

    public List<Product> getProductsByUser(Integer userId) {
        return productMapper.findByUserId(userId);
    }

    public boolean updateProduct(Product product) {
        if (product.getId() == null || productMapper.findById(product.getId()) == null) {
            return false;
        }
        return productMapper.update(product) > 0;
    }

    public boolean deleteProduct(Integer id) {
        return productMapper.deleteById(id) > 0;
    }

    public List<Product> search(String keyword) {
        return productMapper.search(keyword);
    }
}