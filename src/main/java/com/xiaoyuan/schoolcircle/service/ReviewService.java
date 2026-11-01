package com.xiaoyuan.schoolcircle.service;

import com.xiaoyuan.schoolcircle.entity.Order;
import com.xiaoyuan.schoolcircle.entity.Review;
import com.xiaoyuan.schoolcircle.mapper.OrderMapper;
import com.xiaoyuan.schoolcircle.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private OrderMapper orderMapper;

    // 提交评价
    public boolean submitReview(Integer orderId, Integer reviewerId, Integer rating, String content) {
        // 1. 检查订单是否存在
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        // 2. 订单必须是已完成状态（status=3）
        if (order.getStatus() != 3) {
            throw new RuntimeException("订单未完成，不能评价");
        }
        // 3. 检查当前用户是否是该订单的买家或卖家
        Integer revieweeId;
        if (order.getBuyerId().equals(reviewerId)) {
            revieweeId = order.getSellerId();
        } else if (order.getSellerId().equals(reviewerId)) {
            revieweeId = order.getBuyerId();
        } else {
            throw new RuntimeException("无权评价该订单");
        }
        // 4. 检查是否已评价过
        if (reviewMapper.countByOrderAndReviewer(orderId, reviewerId) > 0) {
            throw new RuntimeException("您已评价过该订单");
        }
        // 5. 检查评分范围
        if (rating == null || rating < 1 || rating > 5) {
            throw new RuntimeException("评分必须在1-5之间");
        }

        // 6. 插入评价
        Review review = new Review();
        review.setOrderId(orderId);
        review.setReviewerId(reviewerId);
        review.setRevieweeId(revieweeId);
        review.setRating(rating);
        review.setContent(content);
        reviewMapper.insert(review);

        // 7. 重新计算被评价人的信用分
        updateCreditScore(revieweeId);

        return true;
    }

    // 重新计算并更新信用分
    private void updateCreditScore(Integer userId) {
        Double avg = reviewMapper.getAvgRating(userId);
        if (avg == null) return;
        // 信用分 = 60 + (平均分 - 3) × 15，范围0-100
        int score = (int) Math.round(60 + (avg - 3) * 15);
        score = Math.max(0, Math.min(100, score));
        reviewMapper.updateCreditScore(userId, score);
    }

    // 查询某用户收到的所有评价
    public List<Review> getReviewsByUser(Integer userId) {
        return reviewMapper.findByRevieweeId(userId);
    }

    // 检查某订单某人是否已评价
    public boolean hasReviewed(Integer orderId, Integer reviewerId) {
        return reviewMapper.countByOrderAndReviewer(orderId, reviewerId) > 0;
    }
}
