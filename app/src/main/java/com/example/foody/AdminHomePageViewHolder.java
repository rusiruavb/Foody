package com.example.foody;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AdminHomePageViewHolder extends RecyclerView.ViewHolder {

    public ImageView productImage;
    public TextView productName;
    public TextView productPrice;
    public TextView productQuantity;
    public CardView productCard;

    public AdminHomePageViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.display_cart_product_image);
        productName = itemView.findViewById(R.id.seller_profile_item_name);
        productPrice = itemView.findViewById(R.id.display_seller_item_price);
        productQuantity = itemView.findViewById(R.id.display_seller_item_quantity);
        productCard = itemView.findViewById(R.id.seller_item_card);
    }
}
