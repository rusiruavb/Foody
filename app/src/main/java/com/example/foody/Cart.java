package com.example.foody;

import androidx.cardview.widget.CardView;

public class Cart {
    private String cartId;
    private String itemName;
    private String itemPrice;
    private int itemQuantity;
    private int total;
    private String itemImage;

    public Cart() {}

    public Cart(String cartId, String itemName, String itemPrice, int itemQuantity, int total, String itemImage) {
        this.cartId = cartId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.total = total;
        this.itemImage = itemImage;
    }

    public String getCartId() {
        return cartId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getTotal() {
        return total;
    }

    public String getItemImage() {
        return itemImage;
    }
}
