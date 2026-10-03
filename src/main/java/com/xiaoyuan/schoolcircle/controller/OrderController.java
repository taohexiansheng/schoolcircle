package com.xiaoyuan.schoolcircle.controller;

import com.xiaoyuan.schoolcircle.entity.Order;
import com.xiaoyuan.schoolcircle.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest request;

    private Integer getCurrentUserId() {
        return (Integer) request.getAttribute("userId");
    }

    // 创建订单（买家下单）
    @PostMapping("/create")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = getCurrentUserId();
        if (userId == null) {
            result.put("success", false);
            result.put("message", "未登录");
            return result;
        }
        try {
            Integer productId = Integer.valueOf(params.get("productId").toString());
            String buyerMessage = params.get("buyerMessage") != null ? params.get("buyerMessage").toString() : "";
            Order order = orderService.createOrder(productId, userId, buyerMessage);
            result.put("success", true);
            result.put("order", order);
            result.put("message", "下单成功");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // 我买到的订单
    @GetMapping("/buyer")
    public List<Order> getBuyerOrders() {
        Integer userId = getCurrentUserId();
        if (userId == null) return List.of();
        return orderService.getBuyerOrders(userId);
    }

    // 我卖出的订单
    @GetMapping("/seller")
    public List<Order> getSellerOrders() {
        Integer userId = getCurrentUserId();
        if (userId == null) return List.of();
        return orderService.getSellerOrders(userId);
    }

    // 更新订单状态
    @PutMapping("/{id}/status")
    public String updateStatus(@PathVariable Integer id, @RequestParam Integer status) {
        Integer userId = getCurrentUserId();
        if (userId == null) return "未登录";
        boolean success = orderService.updateOrderStatus(id, status, userId);
        return success ? "更新成功" : "更新失败";
    }
}
