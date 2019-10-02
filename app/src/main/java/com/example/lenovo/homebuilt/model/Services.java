package com.example.lenovo.homebuilt.model;



public class Services {
    private int icon;
    private String name;
    private int price;

    public Services(int icon, String name, int price) {
        this.icon = icon;
        this.name = name;
        this.price = price;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
