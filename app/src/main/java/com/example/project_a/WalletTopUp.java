package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WalletTopUp extends AppCompatActivity {
    Button amount_100, amount_200, amount_250, amount_500;
    float selected_amount;
    FirebaseDatabase db;
    DatabaseReference reference;
    float ret_bal;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_top_up);

        //status ba color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }


        // Displaying the tick mark on payment options
        ImageView apple_pay_tick = findViewById(R.id.apple_correct_mark);
        ImageView googple_pay_tick = findViewById(R.id.gpay_correct_mark);
        ImageButton apple_pay_opt = findViewById(R.id.apple_pay_sel);
        ImageButton google_pay_opt = findViewById(R.id.google_pay_sel);
        LinearLayout select_amounts = findViewById(R.id.select_amount);
        Button proceed_to_pay = findViewById(R.id.continue_payment);

        amount_100 = findViewById(R.id.amount_100);
        amount_200 = findViewById(R.id.amount_200);
        amount_250 = findViewById(R.id.amount_250);
        amount_500 = findViewById(R.id.amount_500);


        // Adding onclick listener to the button
        apple_pay_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apple_pay_tick.setVisibility(View.VISIBLE);
                google_pay_opt.setEnabled(false);
            }
        });

        google_pay_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googple_pay_tick.setVisibility(View.VISIBLE);
                apple_pay_opt.setEnabled(false);
            }
        });

        // Adding onclcik listener

        //
        // TODO: Add gpay, applepay toggle option
        // TODO: Adding color effects and toggle option to the amount selected
        // TODO: Displaying the amount selected from the layout

        proceed_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: update the balance
                db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");
                reference = db.getReference("User_Balance");
                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

                // getting old balance

                if (current_user != null) {
                    String userId = current_user.getUid();


                    reference.child(current_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(getApplicationContext(), "Seraching for balance + " + userId, Toast.LENGTH_SHORT).show();
                            // Handle the data here
                            if (dataSnapshot.exists()) {
                                UserWalletDB value = dataSnapshot.getValue(UserWalletDB.class);
                                // Do something with the data
                                if (value != null) {
                                    ret_bal = value.getWallet_balance();
                                    Toast.makeText(WalletTopUp.this, "Old balance : " + ret_bal, Toast.LENGTH_SHORT).show();
                                    // update the balance
                                    UserWalletDB userWalletDB = new UserWalletDB(ret_bal + selected_amount);
                                    reference.child(current_user.getUid()).setValue(userWalletDB).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(WalletTopUp.this, "Wallet updated", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                } else {
                                    ret_bal = 1;
                                    Toast.makeText(getApplicationContext(), "Not found.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // No data found
                                ret_bal = 2;
                                System.out.println("No data found in Firebase");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors
                            System.out.println("Error reading data from Firebase: " + databaseError.getMessage());
                        }
                    });
                }
                // ----- old ----

                // go to payment complete page
                Intent intent = new Intent(getApplicationContext(), PaymentComplete.class);
                startActivity(intent);
                finish();


            }
        });
    }

    public void getAmount(View v) {

        int is_amount_selected = 0;

        String fin_amount = "Selected amount: ";
        switch (v.getId()) {
            case R.id.amount_100:
                fin_amount += "100";
                amount_100.setBackgroundColor(getResources().getColor(R.color.button_green));
                selected_amount = 100;
                amount_200.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_250.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_500.setBackgroundColor(getResources().getColor(R.color.button_released));
                break;
            case R.id.amount_200:
                fin_amount += "200";
                selected_amount = 200;
                amount_200.setBackgroundColor(getResources().getColor(R.color.button_green));
                amount_100.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_250.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_500.setBackgroundColor(getResources().getColor(R.color.button_released));
                break;
            case R.id.amount_250:
                fin_amount += "250";
                selected_amount = 250;
                amount_250.setBackgroundColor(getResources().getColor(R.color.button_green));
                amount_100.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_200.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_500.setBackgroundColor(getResources().getColor(R.color.button_released));
                break;
            case R.id.amount_500:
                fin_amount += "500";
                selected_amount = 500;
                amount_500.setBackgroundColor(getResources().getColor(R.color.button_green));
                amount_200.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_250.setBackgroundColor(getResources().getColor(R.color.button_released));
                amount_100.setBackgroundColor(getResources().getColor(R.color.button_released));
                break;
        }

        Toast.makeText(this, "" + selected_amount, Toast.LENGTH_SHORT).show();
    }

}