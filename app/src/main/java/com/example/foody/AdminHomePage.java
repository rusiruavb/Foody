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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminHomePage extends Fragment {

    private RecyclerView adminHomeRecyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Items");
    private FirebaseRecyclerOptions<Item> options;
    private FirebaseRecyclerAdapter<Item, AdminHomePageViewHolder> adapter;
    private UpdateProduct updateProduct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home_page, container, false);

        adminHomeRecyclerView = view.findViewById(R.id.admin_home_recycler_view);
        adminHomeRecyclerView.setHasFixedSize(true);
        adminHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateProduct = new UpdateProduct();

        options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(reference, Item.class).build();
        adapter = new FirebaseRecyclerAdapter<Item, AdminHomePageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminHomePageViewHolder holder, int position, @NonNull final Item model) {
                holder.productName.setText(model.getName());
                holder.productPrice.setText(model.getPrice());
                holder.productQuantity.setText(model.getQuantity());
                Picasso.get().load(model.getImage()).centerCrop().fit().into(holder.productImage);

                holder.productCard.setOnClickListener(new View.OnClickListener() {
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
                        updateProduct.setArguments(bundle);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.admin_main_frame, updateProduct);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }

            @NonNull
            @Override
            public AdminHomePageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_admin_homepage_item, parent, false);
                return new AdminHomePageViewHolder(v);
            }
        };

        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        adminHomeRecyclerView.setLayoutManager(gridLayoutManager);
        adminHomeRecyclerView.setAdapter(adapter);

        return view;
    }
}