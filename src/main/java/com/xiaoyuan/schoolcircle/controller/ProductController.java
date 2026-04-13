package com.xiaoyuan.schoolcircle.controller;

import com.xiaoyuan.schoolcircle.entity.Product;
import com.xiaoyuan.schoolcircle.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HttpServletRequest request;

    private Integer getCurrentUserId() {
        return (Integer) request.getAttribute("userId");
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        Integer userId = getCurrentUserId();
        if (userId == null) {
            return "未登录";
        }
        product.setUserId(userId);
        boolean success = productService.addProduct(product);
        return success ? "商品发布成功" : "发布失败";
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/list")
    public List<Product> listProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/user/{userId}")
    public List<Product> listByUser(@PathVariable Integer userId) {
        return productService.getProductsByUser(userId);
    }

    @PutMapping("/update")
    public String updateProduct(@RequestBody Product product) {
        Integer userId = getCurrentUserId();
        if (userId == null) return "未登录";
        // 可选：检查商品是否属于当前用户
        boolean success = productService.updateProduct(product);
        return success ? "更新成功" : "更新失败";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        Integer userId = getCurrentUserId();
        if (userId == null) return "未登录";
        boolean success = productService.deleteProduct(id);
        return success ? "删除成功" : "删除失败";
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }
}