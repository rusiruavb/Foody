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

public class UserRegister extends AppCompatActivity {

    private EditText userName;
    private EditText userEmail;
    private EditText phoneNumber;
    private EditText userPassword;
    private EditText userConformPassword;
    private Button registerButton;
    private TextView createSellerAccount;
    private TextView toUserLogin;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        registerUser();
    }

    private void registerUser() {
        userName = findViewById(R.id.user_register_name);
        userEmail = findViewById(R.id.user_register_email);
        phoneNumber = findViewById(R.id.user_register_phone_number);
        userPassword = findViewById(R.id.user_register_password);
        userConformPassword = findViewById(R.id.user_register_conform_password);
        registerButton = findViewById(R.id.user_register_button);
        createSellerAccount = findViewById(R.id.to_seller_register);
        toUserLogin = findViewById(R.id.to_user_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String phone = phoneNumber.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                final String imageUrl = "https://firebasestorage.googleapis.com/v0/b/foody-ad26d.appspot.com/o/default_profile_image%20(1).png?alt=media&token=3a29ea54-4a0b-42bd-92d0-6344d780d9bd";
                String conformPassword = userConformPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    userName.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    phoneNumber.setError("Phone Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Password is Required");
                    return;
                }
                if (TextUtils.isEmpty(conformPassword)) {
                    userConformPassword.setError("Password is Required");
                    return;
                }
                if (!TextUtils.equals(password, conformPassword)) {
                    userConformPassword.setError("Passwords Not Match");
                    return;
                }

                dialog.setMessage("Sending Data...");
                dialog.show();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            String userId = auth.getCurrentUser().getUid();
                            User newUser = new User(name, email, phone, password, imageUrl);
                            reference.child(userId).setValue(newUser);
                            Toast.makeText(getApplicationContext(),"Registration Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), UserHome.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        createSellerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminRegister.class));
            }
        });

        toUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}