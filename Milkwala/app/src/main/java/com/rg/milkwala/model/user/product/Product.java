package com.rg.milkwala.model.user.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

@SerializedName("availabilityZone")
@Expose
private String availabilityZone;
@SerializedName("description")
@Expose
private String description;
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("image1")
@Expose
private String image1;
@SerializedName("image2")
@Expose
private String image2;
@SerializedName("maximumOrderAmount")
@Expose
private Integer maximumOrderAmount;
@SerializedName("minimumOrderAmount")
@Expose
private Integer minimumOrderAmount;
@SerializedName("name")
@Expose
private String name;
@SerializedName("onetimePrice")
@Expose
private float onetimePrice;
@SerializedName("outOfStock")
@Expose
private Boolean outOfStock;
@SerializedName("subscriptionPrice")
@Expose
private float subscriptionPrice;
@SerializedName("vendorName")
@Expose
private String vendorName;

    @SerializedName("category")
    @Expose
    private int category;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getAvailabilityZone() {
return availabilityZone;
}

public void setAvailabilityZone(String availabilityZone) {
this.availabilityZone = availabilityZone;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getImage1() {
return image1;
}

public void setImage1(String image1) {
this.image1 = image1;
}

public String getImage2() {
return image2;
}

public void setImage2(String image2) {
this.image2 = image2;
}

public Integer getMaximumOrderAmount() {
return maximumOrderAmount;
}

public void setMaximumOrderAmount(Integer maximumOrderAmount) {
this.maximumOrderAmount = maximumOrderAmount;
}

public Integer getMinimumOrderAmount() {
return minimumOrderAmount;
}

public void setMinimumOrderAmount(Integer minimumOrderAmount) {
this.minimumOrderAmount = minimumOrderAmount;
}

public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}

    public float getOnetimePrice() {
        return onetimePrice;
    }

    public void setOnetimePrice(float onetimePrice) {
        this.onetimePrice = onetimePrice;
    }

    public float getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(float subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Boolean getOutOfStock() {
return outOfStock;
}

public void setOutOfStock(Boolean outOfStock) {
this.outOfStock = outOfStock;
}



public String getVendorName() {
return vendorName;
}

public void setVendorName(String vendorName) {
this.vendorName = vendorName;
}
}