package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.Manifest;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView reg_page;
    RegexValidator regexValidator;
    FirebaseAuth mAuth;
    ProgressBar login_progress;

    //    private static final int LOCATION_PERMISSION_CODE = 200;
//    String[] perms = {"android.permission.INTERNET"};
    // check if user is already logged in
    @Override
    public void onStart() {
        super.onStart();

        // check if internet permission is granted, and internet is available
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetInfo = connectivityManager.getActiveNetwork();


        if (activeNetInfo == null) {
            // show offline page
            Intent intent = new Intent(getApplicationContext(), DeviceOffline.class);
            startActivity(intent);
            finish();
        } else {
            // Toast.makeText(this, "Internet available", Toast.LENGTH_SHORT).show();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            mAuth = FirebaseAuth.getInstance();
            if (currentUser != null) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        // Check if user is signed in (non-null) and update UI accordingly.

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //status ba color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }

        email = findViewById(R.id.user_email_addr);
        password = findViewById(R.id.user_password);
        reg_page = findViewById(R.id.text_to_register);
        login = findViewById(R.id.btn_login);
        login_progress = findViewById(R.id.login_progress);
        regexValidator = new RegexValidator();
        mAuth = FirebaseAuth.getInstance();

        // navigate to the register page
        reg_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id, pass;
                email_id = String.valueOf(email.getText());
                pass = String.valueOf(password.getText());

                //show the progress bar
                login_progress.setVisibility(View.VISIBLE);
                // validate the email id
                if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //login process
                mAuth.signInWithEmailAndPassword(email_id, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                login_progress.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}