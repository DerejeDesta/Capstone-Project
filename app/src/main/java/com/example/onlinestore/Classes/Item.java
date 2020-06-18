package com.example.onlinestore.Classes;

public class Item
{
    private String itemName, description, detail,price, image, itemType, itemId, dateSaved;

    public Item() {
    }

    public Item(String itemName, String description,String detail, String price, String image, String itemType, String itemId, String dateSaved) {
        this.itemName = itemName;
        this.description = description;
        this.description = description;
        this.detail=detail;
        this.price = price;
        this.image = image;
        this.itemType = itemType;
        this.itemId = itemId;
        this.dateSaved = dateSaved;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String description) {
        this.detail = description;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType  ) {
        this.itemType = itemType;
    }

    public  String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getdateSaved() {
        return dateSaved;
    }

    public void setdateSaved(String dateSaved) {
        this.dateSaved = dateSaved;
    }


}