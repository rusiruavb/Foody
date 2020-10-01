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

public class MainActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    private TextView toRegisterUser;
    private TextView toAdminAccountLogin;

    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        userLogin();
    }

    private void userLogin() {
        userEmail = findViewById(R.id.user_login_email);
        userPassword = findViewById(R.id.user_login_password);
        loginButton = findViewById(R.id.user_login_button);
        toRegisterUser = findViewById(R.id.create_user_account);
        toAdminAccountLogin = findViewById(R.id.login_as_seller);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Password is Required");
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
                            startActivity(new Intent(getApplicationContext(), UserHome.class));
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Email or Password is Incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        toRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserRegister.class));
            }
        });

        toAdminAccountLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });
    }
}