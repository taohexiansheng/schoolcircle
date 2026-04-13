package com.xiaoyuan.schoolcircle.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;
    private Integer userId;      // 发布者ID
    private String title;
    private String description;
    private BigDecimal price;
    private String images;
    private String campus;
    private Integer status;      // 1:在售 0:已售
    private Date createdAt;

    // getter 和 setter（使用 IDEA 快捷键 Alt+Insert 自动生成）
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public String getCampus() { return campus; }
    public void setCampus(String campus) { this.campus = campus; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}