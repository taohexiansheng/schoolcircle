package com.xiaoyuan.schoolcircle.mapper;

import com.xiaoyuan.schoolcircle.entity.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 插入评价
    @Insert("INSERT INTO review(order_id, reviewer_id, reviewee_id, rating, content) " +
            "VALUES(#{orderId}, #{reviewerId}, #{revieweeId}, #{rating}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Review review);

    // 查询某订单下某人是否已评价
    @Select("SELECT COUNT(*) FROM review WHERE order_id = #{orderId} AND reviewer_id = #{reviewerId}")
    int countByOrderAndReviewer(@Param("orderId") Integer orderId, @Param("reviewerId") Integer reviewerId);

    // 查询某个用户收到的所有评价（带评价人用户名）
    @Select("SELECT r.*, u.username AS reviewerName FROM review r " +
            "LEFT JOIN user u ON r.reviewer_id = u.id " +
            "WHERE r.reviewee_id = #{revieweeId} ORDER BY r.created_at DESC")
    List<Review> findByRevieweeId(Integer revieweeId);

    // 计算某用户的平均评分（如果没有评价，返回null）
    @Select("SELECT AVG(rating) FROM review WHERE reviewee_id = #{revieweeId}")
    Double getAvgRating(Integer revieweeId);

    // 更新用户信用分
    @Update("UPDATE user SET credit_score = #{creditScore} WHERE id = #{userId}")
    int updateCreditScore(@Param("userId") Integer userId, @Param("creditScore") Integer creditScore);
}
