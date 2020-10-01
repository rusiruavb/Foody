package com.example.foody;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddProduct extends Fragment {

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productDescription;
    private ImageView productImage;
    private Button addButton;
    private TextView cancelAddProduct;
    private ProgressDialog dialog;
    private static final int GALLERY_INTENT = 2;
    private Uri imageUri;

    private String sellerName;
    private String sellerEmail;
    private String sellerPhoneNumber;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference sellerReference = database.getReference().child("Admin");
    private DatabaseReference itemReference = database.getReference().child("Items");
    private StorageReference imageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        productName = view.findViewById(R.id.add_product_name);
        productPrice = view.findViewById(R.id.add_product_price);
        productQuantity = view.findViewById(R.id.add_product_quantity);
        productDescription = view.findViewById(R.id.add_product_description);
        productImage = view.findViewById(R.id.add_product_image);
        addButton = view.findViewById(R.id.add_product_button);
        cancelAddProduct = view.findViewById(R.id.add_product_cancel);

        dialog = new ProgressDialog(getContext());
        auth = FirebaseAuth.getInstance();
        imageReference = FirebaseStorage.getInstance().getReference("Products");

        sellerReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sellerName = snapshot.child("name").getValue().toString();
                sellerEmail = snapshot.child("email").getValue().toString();
                sellerPhoneNumber = snapshot.child("phoneNumber").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "ProductImage"), GALLERY_INTENT);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendProductItem();
            }
        });

        return view;
    }

    private void sendProductItem() {
        if (imageUri != null) {
            dialog.setMessage("Adding Item...");
            dialog.show();

            final StorageReference imageRef = imageReference.child(System.currentTimeMillis() + "." + GetFileExtension(imageUri));
            imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String name = productName.getText().toString().trim();
                    final String price = productPrice.getText().toString().trim();
                    final String quantity = productQuantity.getText().toString().trim();
                    final String description = productDescription.getText().toString().trim();
                    final String itemId = itemReference.push().getKey();

                    dialog.dismiss();
                    Toast.makeText(getContext(), "Item Added", Toast.LENGTH_LONG).show();

                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Item newItem = new Item(itemId, name, price, quantity, description, sellerName, sellerEmail, sellerPhoneNumber, url);
                            itemReference.child(itemId).setValue(newItem);

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.admin_main_frame, new AdminHomePage());
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(getContext(), "Please Select An Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                productImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}