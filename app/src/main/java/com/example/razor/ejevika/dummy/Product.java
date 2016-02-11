package com.example.razor.ejevika.dummy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by razor on 07.02.2016.
 */
public class Product implements Parcelable {
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

    protected Product(Parcel in) {
        id = in.readLong();
        name = in.readString();
        picture = in.readString();
        sectionId = in.readLong();
        price = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeLong(sectionId);
        dest.writeDouble(price);
    }
}
