package com.xiaoyuan.schoolcircle.service;

import com.xiaoyuan.schoolcircle.entity.Order;
import com.xiaoyuan.schoolcircle.entity.Product;
import com.xiaoyuan.schoolcircle.mapper.OrderMapper;
import com.xiaoyuan.schoolcircle.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    // 创建订单
    public Order createOrder(Integer productId, Integer buyerId, String buyerMessage) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        if (product.getStatus() != 1) {
            throw new RuntimeException("商品已售出或已下架");
        }
        if (product.getUserId().equals(buyerId)) {
            throw new RuntimeException("不能购买自己发布的商品");
        }
        // 检查是否已被其他人下单
        if (orderMapper.countActiveOrdersByProductId(productId) > 0) {
            throw new RuntimeException("该商品已被下单");
        }

        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setProductId(productId);
        order.setBuyerId(buyerId);
        order.setSellerId(product.getUserId());
        order.setPrice(product.getPrice());
        order.setStatus(0); // 待付款
        order.setBuyerMessage(buyerMessage);

        orderMapper.insert(order);

        // 商品标记为已售
        productMapper.updateStatus(productId, 0);

        return order;
    }

    // 生成订单号：时间戳 + 随机数
    private String generateOrderNo() {
        return System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }

    // 我买到的订单
    public List<Order> getBuyerOrders(Integer buyerId) {
        return orderMapper.findByBuyerId(buyerId);
    }

    // 我卖出的订单
    public List<Order> getSellerOrders(Integer sellerId) {
        return orderMapper.findBySellerId(sellerId);
    }

    // 更新订单状态
    public boolean updateOrderStatus(Integer orderId, Integer status, Integer currentUserId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            return false;
        }
        // 权限校验：只有买家或卖家能操作
        if (!order.getBuyerId().equals(currentUserId) && !order.getSellerId().equals(currentUserId)) {
            return false;
        }
        return orderMapper.updateStatus(orderId, status) > 0;
    }
}
