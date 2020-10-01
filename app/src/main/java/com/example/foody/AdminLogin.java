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

public class AdminLogin extends AppCompatActivity {

    private EditText adminEmail;
    private EditText adminPassword;
    private Button adminLoginButton;
    private TextView createAdminAccount;
    private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        adminLogin();
    }

    private void adminLogin() {
        adminEmail = findViewById(R.id.admin_login_email);
        adminPassword = findViewById(R.id.admin_login_password);
        adminLoginButton = findViewById(R.id.admin_login_button);
        createAdminAccount = findViewById(R.id.create_admin_account);

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = adminEmail.getText().toString().trim();
                String password = adminPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    adminEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    adminPassword.setError("Password is Required");
                    return;
                }

                dialog.setMessage("Login in...");
                dialog.show();

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminHome.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Email or Password is Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        createAdminAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminRegister.class));
            }
        });
    }
}