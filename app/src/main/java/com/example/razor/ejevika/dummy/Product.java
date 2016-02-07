package com.example.razor.ejevika.dummy;

/**
 * Created by razor on 07.02.2016.
 */
public class Product {
   // ID":229,"NAME":"Латте","PICTURE":"\/upload\/iblock\/bc0\/latte.jpg","IBLOCK_SECTION_ID":"17","PRICE":130
    private long id;
    private String name;
    private String picture;
    private long sectionId;
    private double price;

    public Product(){

    }

    public Product(long id, String name, String picture, long sectionId, double price) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.sectionId = sectionId;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public long getSectionId() {
        return sectionId;
    }

    public double getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
