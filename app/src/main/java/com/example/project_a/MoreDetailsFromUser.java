package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MoreDetailsFromUser extends AppCompatActivity {
    Button getStarted;

    EditText mail_id, full_name, mobile_no, address;
    String s_mail_id, s_full_name, s_mobile_no, s_address, default_addr;
    CheckBox set_def_addr;
    ProgressBar dbProg;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details_from_user);

        //status ba color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }

        getStarted = findViewById(R.id.get_started);
        mail_id = findViewById(R.id.get_user_email);
        full_name = findViewById(R.id.get_full_name);
        mobile_no = findViewById(R.id.get_user_phone);
        address = findViewById(R.id.get_user_addr);
        set_def_addr = findViewById(R.id.set_as_default_addr);
        dbProg = findViewById(R.id.db_prog_bar);


        // TODO: set this address as default if the location is not available
        // TODO: validate mobile number
        // TODO: add database code

        // onclick listener for going to the homepage
        getStarted.setOnClickListener(new View.OnClickListener() {
            //display the progress bar
            @Override
            public void onClick(View v) {
                // Add the data to the database before proceeding
                dbProg.setVisibility(View.VISIBLE);
                s_mail_id = mail_id.getText().toString();
                s_full_name = full_name.getText().toString();
                s_mobile_no = mobile_no.getText().toString();
                s_address = address.getText().toString();

                Toast.makeText(MoreDetailsFromUser.this, "hdjk: " + s_full_name, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MoreDetailsFromUser.this, "Hello", Toast.LENGTH_SHORT).show();
                // check if the checkbox is clicked
                if (set_def_addr.isChecked() == true) {
                    default_addr = s_address;
                } else {
                    default_addr = "";
                }

                // validate data before proceeding
                Users user = new Users(s_mail_id, s_full_name, s_mobile_no, s_address, default_addr);
                db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");
                reference = db.getReference("Users");
                reference.child(s_full_name).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        mail_id.setText("");
                        full_name.setText("");
                        mobile_no.setText("");
                        address.setText("");
                        default_addr = "";
                        set_def_addr.setChecked(false);


                        Toast.makeText(MoreDetailsFromUser.this, "Successfully created new user", Toast.LENGTH_SHORT).show();

                    }
                });

                dbProg.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}