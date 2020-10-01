package com.example.foody;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProduct extends Fragment {

    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productDescription;
    private Button updateButton;
    private Button deleteButton;
    private TextView cancelUpdate;
    private ProgressDialog dialog;
    private Bundle bundle;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Items");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_product, container, false);

        productName = view.findViewById(R.id.update_product_name);
        productPrice = view.findViewById(R.id.update_product_price);
        productQuantity = view.findViewById(R.id.update_product_quantity);
        productDescription = view.findViewById(R.id.update_product_description);
        updateButton = view.findViewById(R.id.update_product_button);
        deleteButton = view.findViewById(R.id.delete_product_button);
        cancelUpdate = view.findViewById(R.id.update_product_cancel);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());
        bundle = getArguments();

        renderProductDetails();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Delete Item");
                alertDialog.setMessage("Do you want to delete this item ?");
                alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteProduct();
                    }
                });
                alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        return view;
    }

    private void renderProductDetails() {
        productName.setText(bundle.getString("itemName"));
        productPrice.setText(bundle.getString("itemPrice"));
        productQuantity.setText(bundle.getString("itemQuantity"));
        productDescription.setText(bundle.getString("itemDescription"));
    }

    private void updateProduct() {
        String name = productName.getText().toString().trim();
        String price = productPrice.getText().toString().trim();
        String quantity = productQuantity.getText().toString().trim();
        String description = productDescription.getText().toString().trim();
        String sellerName = bundle.getString("sellerName");
        String sellerEmail = bundle.getString("sellerEmail");
        String sellerPhoneNumber = bundle.getString("sellerPhoneNumber");
        String productImage = bundle.getString("image");
        String itemId = bundle.getString("itemId");

        dialog.setMessage("Updating Product...");
        dialog.show();

        Item updateItem = new Item(itemId, name, price, quantity, description, sellerName,sellerEmail,sellerPhoneNumber, productImage);

        reference.child(itemId).setValue(updateItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Item Updated", Toast.LENGTH_LONG).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.admin_main_frame, new AdminHomePage());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Item Update Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteProduct() {
        String itemId = bundle.getString("itemId");
        reference.child(itemId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_LONG).show();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.admin_main_frame, new AdminHomePage());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Item Delete Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}