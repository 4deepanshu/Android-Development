package com.rg.milkwala.model.user;

/**
 * Created by Mobile on 1/13/2017.
 */

public class Orders {
    String userId;
    String email;
    String deliveryStatus;
    String userName;
    Boolean monthly;
    Boolean once;
    Float mothlyPrice;
    Float oncePrice;
    int productId;
    String quantity;
    String deliveryDate;
    String ordereddate;
    String image;
    String productName;
    String orderStatus;
    String orderCounts;

    /// this is done for student purpose
    boolean isDelivered;

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getMonthly() {
        return monthly;
    }

    public void setMonthly(Boolean monthly) {
        this.monthly = monthly;
    }

    public Boolean getOnce() {
        return once;
    }

    public void setOnce(Boolean once) {
        this.once = once;
    }

    public Float getMothlyPrice() {
        return mothlyPrice;
    }

    public void setMothlyPrice(Float mothlyPrice) {
        this.mothlyPrice = mothlyPrice;
    }

    public Float getOncePrice() {
        return oncePrice;
    }

    public void setOncePrice(Float oncePrice) {
        this.oncePrice = oncePrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrdereddate() {
        return ordereddate;
    }

    public void setOrdereddate(String ordereddate) {
        this.ordereddate = ordereddate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderCounts() {
        return orderCounts;
    }

    public void setOrderCounts(String orderCounts) {
        this.orderCounts = orderCounts;
    }
}
