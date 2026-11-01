package com.xiaoyuan.schoolcircle.entity;

import java.util.Date;

public class Review {
    private Integer id;
    private Integer orderId;
    private Integer reviewerId;
    private Integer revieweeId;
    private Integer rating;
    private String content;
    private Date createdAt;

    // 额外字段：评价人用户名（用于展示）
    private String reviewerName;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getReviewerId() { return reviewerId; }
    public void setReviewerId(Integer reviewerId) { this.reviewerId = reviewerId; }

    public Integer getRevieweeId() { return revieweeId; }
    public void setRevieweeId(Integer revieweeId) { this.revieweeId = revieweeId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
}
