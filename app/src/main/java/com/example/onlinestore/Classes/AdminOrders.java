package com.example.onlinestore.Classes;

public class AdminOrders
{
    private String  email, date, time, totalAmount,status;

    public AdminOrders() {
    }

    public AdminOrders(String email, String date, String time, String totalAmount, String status) {
        this.email = email;
        this.date = date;
        this.time = time;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}