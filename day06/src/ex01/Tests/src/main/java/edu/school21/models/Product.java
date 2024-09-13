package edu.school21.models;


public class Product {

    private final long id_;
    private String name_;
    private float price_;


    public Product(long id) {
        id_ = id;
    }

    public Product(long id, String name, float price) {
        id_ = id;
        name_ = name;
        price_ = price;
    }

    public long getId() {
        return id_;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public float getPrice() {
        return price_;
    }

    public void setPrice(float price) {
        price_ = price;
    }
    
}
