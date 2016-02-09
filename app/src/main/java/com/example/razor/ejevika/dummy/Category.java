package com.example.razor.ejevika.dummy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by razor on 01.02.2016.
 */
public class Category implements Parcelable {

    private long id;
    private String picture;
    private String name;



    public Category(){}

    public Category(long id, String picture, String name){
        this.id = id;
        this.picture = picture;
        this.name = name;
    }

    protected Category(Parcel in) {
        id = in.readLong();
        picture = in.readString();
        name = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass()!=this.getClass())
            return false;
        Category tmp = (Category)o;
        if(tmp.getId()==this.id &&
                tmp.getName().equals(this.getName()) &&
                tmp.getPicture().equals(this.getPicture())){
            return true;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "\nID: "+id+
                "\nNAME: "+name+
                "\nPICTURE: "+picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(picture);
        dest.writeString(name);
    }
}
