package com.example.foody;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserHomePage extends Fragment {

    private RecyclerView userHomeRecyclerView;
    private FirebaseRecyclerOptions<Item> options;
    private FirebaseRecyclerAdapter<Item, UserHomePageViewHolder> adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Items");
    private ProductPage productPage;
    private DatabaseReference cartReference = database.getReference().child("Cart");
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home_page, container, false);

        userHomeRecyclerView = view.findViewById(R.id.user_home_page_recycler_view);
        userHomeRecyclerView.setHasFixedSize(true);
        userHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productPage = new ProductPage();
        auth = FirebaseAuth.getInstance();
        cartReference = cartReference.child(auth.getCurrentUser().getUid());

        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(reference, Item.class).build();
        adapter = new FirebaseRecyclerAdapter<Item, UserHomePageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserHomePageViewHolder holder, int position, @NonNull final Item model) {
                holder.userProductName.setText(model.getName());
                holder.userProductPrice.setText(model.getPrice());
                holder.userProductQuantity.setText(model.getQuantity());
                Picasso.get().load(model.getImage()).centerCrop().fit().into(holder.userProductImage);

                holder.userProductCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("itemName", model.getName());
                        bundle.putString("itemPrice", model.getPrice());
                        bundle.putString("itemQuantity", model.getQuantity());
                        bundle.putString("itemDescription", model.getDescription());
                        bundle.putString("sellerName", model.getSellerName());
                        bundle.putString("sellerEmail", model.getSellerEmail());
                        bundle.putString("sellerPhoneNumber", model.getSellerPhoneNumber());
                        bundle.putString("image", model.getImage());
                        bundle.putString("itemId", model.getItemId());
                        productPage.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.user_main_frame, productPage);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemName = model.getName();
                        String itemPrice = model.getPrice();
                        String image = model.getImage();
                        String cartId = cartReference.push().getKey();
                        int quantity = 1;
                        int total = Integer.parseInt(itemPrice);

                        Cart newItem = new Cart(cartId, itemName, itemPrice, quantity, total, image);
                        cartReference.child(cartId).setValue(newItem);
                        Toast.makeText(getContext(), "Item Added to Cart", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public UserHomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_homepage_item, parent, false);
                return new UserHomePageViewHolder(v);
            }
        };

        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        userHomeRecyclerView.setLayoutManager(gridLayoutManager);
        userHomeRecyclerView.setAdapter(adapter);

        return view;
    }
}