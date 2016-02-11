package com.example.razor.ejevika.dummy;

/**
 * Created by Администратор on 10.02.2016.
 */
public class BasketItem {

    private long productId;
    private String productName;
    private String productPicture;
    private double productPrice;
    private int productCount;

    public BasketItem() {
    }

    public BasketItem(long productId, String productName, String productPicture, double prodouctPrice, int productCount) {
        this.productId = productId;
        this.productName = productName;
        this.productPicture = productPicture;
        this.productPrice = prodouctPrice;
        this.productCount = productCount;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
