package com.xiaoyuan.schoolcircle.controller;

import com.xiaoyuan.schoolcircle.entity.Review;
import com.xiaoyuan.schoolcircle.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HttpServletRequest request;

    private Integer getCurrentUserId() {
        return (Integer) request.getAttribute("userId");
    }

    // 提交评价
    @PostMapping("/submit")
    public Map<String, Object> submit(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Integer userId = getCurrentUserId();
        if (userId == null) {
            result.put("success", false);
            result.put("message", "未登录");
            return result;
        }
        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Integer rating = Integer.valueOf(params.get("rating").toString());
            String content = params.get("content") != null ? params.get("content").toString() : "";
            boolean success = reviewService.submitReview(orderId, userId, rating, content);
            result.put("success", success);
            result.put("message", success ? "评价成功" : "评价失败");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // 查询某用户收到的评价
    @GetMapping("/user/{userId}")
    public List<Review> getUserReviews(@PathVariable Integer userId) {
        return reviewService.getReviewsByUser(userId);
    }

    // 检查某订单当前用户是否已评价
    @GetMapping("/check")
    public boolean checkReviewed(@RequestParam Integer orderId) {
        Integer userId = getCurrentUserId();
        if (userId == null) return false;
        return reviewService.hasReviewed(orderId, userId);
    }
}
