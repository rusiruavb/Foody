package com.example.foody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegister extends AppCompatActivity {

    private EditText adminName;
    private EditText adminEmail;
    private EditText adminPhoneNumber;
    private EditText adminPassword;
    private EditText adminConformPassword;
    private Button registerAdminButton;
    private TextView toAdminLogin;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        registerAdmin();
    }

    private void registerAdmin() {
        adminName = findViewById(R.id.seller_register_name);
        adminEmail = findViewById(R.id.seller_register_email);
        adminPhoneNumber = findViewById(R.id.seller_register_phone_number);
        adminPassword = findViewById(R.id.seller_register_password);
        adminConformPassword = findViewById(R.id.seller_register_conform_password);
        registerAdminButton = findViewById(R.id.seller_register_button);
        toAdminLogin = findViewById(R.id.to_seller_login);

        registerAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = adminName.getText().toString().trim();
                final String email = adminEmail.getText().toString().trim();
                final String phone = adminPhoneNumber.getText().toString().trim();
                final String password = adminPassword.getText().toString().trim();
                final String imageUrl = "https://firebasestorage.googleapis.com/v0/b/foody-ad26d.appspot.com/o/default_profile_image%20(1).png?alt=media&token=3a29ea54-4a0b-42bd-92d0-6344d780d9bd";
                String conformPassword = adminConformPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    adminName.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    adminEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    adminPhoneNumber.setError("Phone Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    adminPassword.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(conformPassword)) {
                    adminConformPassword.setError("Password is Required");
                    return;
                }
                if (!TextUtils.equals(password, conformPassword)) {
                    adminConformPassword.setError("Passwords Not Match");
                    return;
                }

                dialog.setMessage("Sending Data...");
                dialog.show();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Admin newAdmin = new Admin(name, email, phone, password, imageUrl);
                            String userId = auth.getCurrentUser().getUid();
                            reference.child(userId).setValue(newAdmin);
                            Toast.makeText(getApplicationContext(),"Registration Success", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(), UserHome.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        toAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminRegister.class));
            }
        });
    }
}