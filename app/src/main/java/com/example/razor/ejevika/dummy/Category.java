package com.example.razor.ejevika.dummy;

/**
 * Created by razor on 01.02.2016.
 */
public class Category {

    private long id;
    private String picture;
    private String name;



    public Category(){}

    public Category(long id, String picture, String name){
        this.id = id;
        this.picture = picture;
        this.name = name;
    }

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
}
