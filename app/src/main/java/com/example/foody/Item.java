package com.example.foody;

public class Item {
    private String itemId;
    private String name;
    private String price;
    private String quantity;
    private String description;
    private String sellerName;
    private String sellerEmail;
    private String sellerPhoneNumber;
    private String image;

    public Item() {}

    public Item(String itemId, String name, String price, String quantity, String description, String sellerName, String sellerEmail, String sellerPhoneNumber, String image) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
        this.sellerPhoneNumber = sellerPhoneNumber;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getItemId() {
        return itemId;
    }
}
