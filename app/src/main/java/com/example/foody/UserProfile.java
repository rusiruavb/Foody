package com.example.foody;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends Fragment {

    private ImageView userProfileImage;
    private TextView userName;
    private TextView userEmail;
    private RecyclerView purchasedItemsRecyclerView;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference profileReference = database.getReference().child("Users");
    private DatabaseReference conformedPaymentReference = database.getReference().child("User_Payments");

    private RecyclerView purchasedDetailsRecyclerView;
    private FirebaseRecyclerOptions<ConformedPayment> options;
    private FirebaseRecyclerAdapter<ConformedPayment, ConformedPaymentViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        userProfileImage = view.findViewById(R.id.user_profile_image);
        userName = view.findViewById(R.id.user_profile_name);
        userEmail = view.findViewById(R.id.user_profile_email);
        purchasedDetailsRecyclerView = view.findViewById(R.id.purchased_items_recycler_view);
        purchasedDetailsRecyclerView.setHasFixedSize(true);
        purchasedDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();

        displayUserProfileDetails();

        options = new FirebaseRecyclerOptions.Builder<ConformedPayment>().setQuery(conformedPaymentReference.child(auth.getCurrentUser().getUid()), ConformedPayment.class).build();
        adapter = new FirebaseRecyclerAdapter<ConformedPayment, ConformedPaymentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ConformedPaymentViewHolder holder, int position, @NonNull ConformedPayment model) {
                holder.paymentId.setText(model.getPaymentId());
                holder.paymentDate.setText(model.getDate());
                holder.totalPrice.setText(model.getTotalPrice());
            }

            @NonNull
            @Override
            public ConformedPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_purchased_item, parent, false);
                return new ConformedPaymentViewHolder(v);
            }
        };

        adapter.startListening();
        purchasedDetailsRecyclerView.setAdapter(adapter);

        return view;
    }

    private void displayUserProfileDetails() {
        profileReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("image").getValue().toString()).centerCrop().fit().into(userProfileImage);
                userName.setText(snapshot.child("name").getValue().toString());
                userEmail.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}