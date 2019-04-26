package com.sheehabe.tourmate.pojo;

public class ExpenseInfo {

    public String expenseID;
    public String imageURL;

    public String typeName;
    public String date;
    public String cost,time;
    public int key;

    public ExpenseInfo(String typeName, String date, String cost, String time) {
        this.typeName = typeName;
        this.date = date;
        this.cost = cost;
        this.time = time;
    }

    public ExpenseInfo(String typeName, String cost, String date, String time, String imageURL) {

        this.typeName = typeName;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.imageURL = imageURL;
    }

    public ExpenseInfo() {
        this.expenseID = expenseID;
        this.imageURL = imageURL;
        this.typeName = typeName;
        this.date = date;
        this.cost = cost;
        this.time = time;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
