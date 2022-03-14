package com.example.abim.lks_hotel4_mobile_3_2;

public class Cart {
    private int room_id, fdId, totalPrice, qty, employeeId;
    private String fdName;

    public Cart(int room_id, int fdId, int totalPrice, int qty, int employeeId, String fdName) {
        this.room_id = room_id;
        this.fdId = fdId;
        this.totalPrice = totalPrice;
        this.qty = qty;
        this.employeeId = employeeId;
        this.fdName = fdName;
    }

    public int getRoom_id() {
        return room_id;
    }

    public int getFdId() {
        return fdId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQty() {
        return qty;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getFdName() {
        return fdName;
    }
}
