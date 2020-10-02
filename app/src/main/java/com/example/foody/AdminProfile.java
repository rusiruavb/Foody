package com.example.foody;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminProfile extends Fragment {

    private ImageView profileImage;
    private TextView sellerName;
    private TextView sellerEmail;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Admin");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        profileImage = view.findViewById(R.id.admin_image);
        sellerName = view.findViewById(R.id.admin_name);
        sellerEmail = view.findViewById(R.id.admin_email);
        auth = FirebaseAuth.getInstance();

        reference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("imageUrl").getValue().toString()).into(profileImage);
                sellerName.setText(snapshot.child("name").getValue().toString());
                sellerEmail.setText(snapshot.child("email").getValue().toString());
            }

            // need to add recycle view logic to admin profile interface

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        return view;
    }
}