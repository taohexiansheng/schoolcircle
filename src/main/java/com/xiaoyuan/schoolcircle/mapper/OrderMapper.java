package com.xiaoyuan.schoolcircle.mapper;

import com.xiaoyuan.schoolcircle.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 创建订单
    @Insert("INSERT INTO `order`(order_no, product_id, buyer_id, seller_id, price, status, buyer_message) " +
            "VALUES(#{orderNo}, #{productId}, #{buyerId}, #{sellerId}, #{price}, #{status}, #{buyerMessage})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    // 查询我买到的订单（关联商品标题和图片）
    @Select("SELECT o.*, p.title AS productTitle, p.images AS productImages " +
            "FROM `order` o LEFT JOIN product p ON o.product_id = p.id " +
            "WHERE o.buyer_id = #{buyerId} ORDER BY o.created_at DESC")
    List<Order> findByBuyerId(Integer buyerId);

    // 查询我卖出的订单
    @Select("SELECT o.*, p.title AS productTitle, p.images AS productImages " +
            "FROM `order` o LEFT JOIN product p ON o.product_id = p.id " +
            "WHERE o.seller_id = #{sellerId} ORDER BY o.created_at DESC")
    List<Order> findBySellerId(Integer sellerId);

    // 根据ID查询订单
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order findById(Integer id);

    // 更新订单状态
    @Update("UPDATE `order` SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    // 检查商品是否已被下单（防止重复购买）
    @Select("SELECT COUNT(*) FROM `order` WHERE product_id = #{productId} AND status IN (0,1,2)")
    int countActiveOrdersByProductId(Integer productId);
}
