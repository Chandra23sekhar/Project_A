package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookHatchBack extends AppCompatActivity {
    Button pay_now;
    ProgressBar progBar;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hatch_back);

        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.darkpurple));
        }

        pay_now = findViewById(R.id.pay_now);
        progBar = findViewById(R.id.pay_prog);


        // update wallet on successful completion of booking
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display the progress bar
                progBar.setVisibility(View.VISIBLE);

                // update the wallet balance
                db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");
                reference = db.getReference("User_balance");
                String curr_user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //get the existing balance
                reference.child(curr_user).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserWalletDB userWalletDB = dataSnapshot.getValue(UserWalletDB.class);

                        if (dataSnapshot.exists()) {
                            if (userWalletDB != null) {
                                //  Toast.makeText(BookHatchBack.this, "Old balance : " + userWalletDB.getWallet_balance(), Toast.LENGTH_LONG).show();

                                float new_balance = userWalletDB.getWallet_balance() - 380.0f;
                                // Toast.makeText(BookHatchBack.this, "New balance : " + new_balance, Toast.LENGTH_SHORT).show();


                                // book only if balane is avail
                                if (userWalletDB.getWallet_balance() >= 380) {
                                    //   Toast.makeText(BookHatchBack.this, "New balance : " + new_balance, Toast.LENGTH_SHORT).show();

                                    userWalletDB.setWallet_balance(new_balance);
                                    reference.child(curr_user).setValue(userWalletDB).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            //Toast.makeText(BookHatchBack.this, "Transaction complete", Toast.LENGTH_SHORT).show();

                                            // remove the progressbar and return to the ----- page
                                            progBar.setVisibility(View.GONE);

                                            // send the notification
                                            sendNotification(true);

                                            Intent intent = new Intent(getApplicationContext(), BookingSuccess.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    // User does not have sufficient balance
                                    Toast.makeText(BookHatchBack.this, "Insufficient Balance", Toast.LENGTH_SHORT).show();

                                    //send failed notification
                                    sendNotification(false);

                                    Intent intent = new Intent(getApplicationContext(), BookingFailed.class);
                                    startActivity(intent);
                                    finish();
                                }


                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void sendNotification(Boolean status) {
        // send notification on success or failure


        Log.d("Notification", "Sending notification");
        NotificationChannel channel = new NotificationChannel("id", "new_notification", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = ContextCompat.getSystemService(getApplicationContext(), NotificationManager.class);
        notificationManager.createNotificationChannel(channel);


        String title, text;
        int id;

        if (status) {
            id = 1;
            title = "Booking Confirmed";
            text = "Your rental for POLO GT has been confirmed";
        } else {
            id = 2;
            title = "Booking Failed";
            text = "Your rental for POLO GT has failed";
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "id")
                .setSmallIcon(R.drawable.baseline_car_rental_24)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(id, builder.build());
    }
}