package com.example.project_a;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseUser currentUser;
    TextView curr_bal;

    public WalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);


        db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");
        reference = db.getReference("User_balance");
        curr_bal = view.findViewById(R.id.user_current_balance);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
//            Toast.makeText(getContext(), "hdskj + " + userId, Toast.LENGTH_SHORT).show();

            reference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Handle the data here
                    if (dataSnapshot.exists()) {
                        UserWalletDB value = dataSnapshot.getValue(UserWalletDB.class);
                        // Do something with the data
                        if (value != null) {
                            curr_bal.setText("₹ " + value.getWallet_balance());

                        } else {
                            Toast.makeText(getContext(), "Not found.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        // No data found
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


        Button top_up = view.findViewById(R.id.top_up_submit);
        // add intent to go to payments page on submit
        top_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_payments = new Intent(getContext(), WalletTopUp.class);
                startActivity(go_to_payments);
            }
        });

        return view;
    }


}