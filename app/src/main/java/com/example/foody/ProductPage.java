package com.example.foody;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductPage extends Fragment {

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productQuantity;
    private TextView sellerName;
    private TextView sellerEmail;
    private TextView sellerPhoneNumber;
    private TextView productDescription;
    private Button addToCart;
    private Bundle bundle;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference cartReference = database.getReference().child("Cart");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        productImage = view.findViewById(R.id.product_display_image);
        productName = view.findViewById(R.id.product_display_name);
        productPrice = view.findViewById(R.id.product_display_price);
        productQuantity = view.findViewById(R.id.product_display_quantity);
        productDescription = view.findViewById(R.id.display_product_description);
        sellerName = view.findViewById(R.id.product_display_seller_name);
        sellerEmail = view.findViewById(R.id.product_display_seller_email);
        sellerPhoneNumber = view.findViewById(R.id.product_display_seller_phone_number);
        addToCart = view.findViewById(R.id.product_display_add_to_cart_button);
        auth = FirebaseAuth.getInstance();
        bundle = getArguments();
        cartReference = cartReference.child(auth.getCurrentUser().getUid());

        displayProductDetails();

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = bundle.getString("itemName");
                String itemPrice = bundle.getString("itemPrice");
                String image = bundle.getString("image");
                String cartId = cartReference.push().getKey();
                int quantity = 1;
                int total = Integer.parseInt(itemPrice);

                Cart newItem = new Cart(cartId, itemName, itemPrice, quantity, total, image);
                cartReference.child(cartId).setValue(newItem);
                Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void displayProductDetails() {
        Picasso.get().load(bundle.getString("image")).fit().into(productImage);
        productName.setText(bundle.getString("itemName"));
        productPrice.setText(bundle.getString("itemPrice"));
        productQuantity.setText(bundle.getString("itemQuantity"));
        sellerName.setText(bundle.getString("sellerName"));
        sellerEmail.setText(bundle.getString("sellerEmail"));
        sellerPhoneNumber.setText(bundle.getString("sellerPhoneNumber"));
        productDescription.setText(bundle.getString("itemDescription"));
    }
}