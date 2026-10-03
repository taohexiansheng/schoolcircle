package com.xiaoyuan.schoolcircle.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private Integer id;
    private String orderNo;
    private Integer productId;
    private Integer buyerId;
    private Integer sellerId;
    private BigDecimal price;
    private Integer status; // 0待付款 1待发货 2待收货 3已完成 4已取消
    private String buyerMessage;
    private Date createdAt;
    private Date updatedAt;

    // 额外字段：商品标题和图片（用于前端展示）
    private String productTitle;
    private String productImages;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Integer getBuyerId() { return buyerId; }
    public void setBuyerId(Integer buyerId) { this.buyerId = buyerId; }

    public Integer getSellerId() { return sellerId; }
    public void setSellerId(Integer sellerId) { this.sellerId = sellerId; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getBuyerMessage() { return buyerMessage; }
    public void setBuyerMessage(String buyerMessage) { this.buyerMessage = buyerMessage; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public String getProductTitle() { return productTitle; }
    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public String getProductImages() { return productImages; }
    public void setProductImages(String productImages) { this.productImages = productImages; }
}