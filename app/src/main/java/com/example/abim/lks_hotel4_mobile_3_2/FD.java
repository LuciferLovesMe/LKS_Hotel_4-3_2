package com.example.abim.lks_hotel4_mobile_3_2;

public class FD {
    private int id, price;
    private String name;

    public FD(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
