package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    EditText email, password, re_password;
    Button register;
    TextView login_page;
    RegexValidator regexValidator;
    FirebaseAuth mAuth;
    ProgressBar register_progress;

    // check if user is already logged in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    // TODO: add field to verify password, before creating an account
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        register = findViewById(R.id.btn_register);
        login_page = findViewById(R.id.text_to_login);
        re_password = findViewById(R.id.retype_password);
        regexValidator = new RegexValidator();
        register_progress = findViewById(R.id.register_progress);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_id, pass, re_pass;
                email_id = String.valueOf(email.getText());
                pass = String.valueOf(password.getText());
                re_pass = String.valueOf(re_password.getText());


                // validate the email id
                if (TextUtils.isEmpty(email_id)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.equals(re_pass) == false) {
                    Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //show the progress bar
                register_progress.setVisibility(View.VISIBLE);


                // register process
                mAuth.createUserWithEmailAndPassword(email_id, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    register_progress.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(getApplicationContext(), MoreDetailsFromUser.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    register_progress.setVisibility(View.GONE);
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        // open login page from register
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


    }
}