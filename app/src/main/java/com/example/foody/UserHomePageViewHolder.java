package com.example.foody;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserHomePageViewHolder extends RecyclerView.ViewHolder {

    public ImageView userProductImage;
    public TextView userProductName;
    public TextView userProductPrice;
    public TextView userProductQuantity;
    public CardView userProductCard;
    public Button addToCartButton;

    public UserHomePageViewHolder(@NonNull View itemView) {
        super(itemView);
        userProductImage = itemView.findViewById(R.id.display_cart_product_image);
        userProductName = itemView.findViewById(R.id.seller_profile_item_name);
        userProductPrice = itemView.findViewById(R.id.user_home_item_price);
        userProductQuantity = itemView.findViewById(R.id.user_home_item_quantity);
        userProductCard = itemView.findViewById(R.id.item_card);
        addToCartButton = itemView.findViewById(R.id.add_to_cart);
    }
}
