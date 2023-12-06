package com.example.project_a;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView uName = view.findViewById(R.id.userName);

        // get details of logged in user
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");
        DatabaseReference reference = db.getReference("Users");
        DatabaseReference user_to_find = reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        user_to_find.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users ret_user = dataSnapshot.getValue(Users.class);

                if (ret_user != null) {

                    Toast.makeText(getContext(), ret_user.getFull_name() + user_to_find, Toast.LENGTH_SHORT).show();
                    uName.setText(ret_user.getFull_name());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button log_out_btn = view.findViewById(R.id.log_out);
        FirebaseUser user = auth.getCurrentUser();
        Button edit_profile = view.findViewById(R.id.editProfile);
        EditText edit_phone = view.findViewById(R.id.edit_phone);
        TextView norm_phone = view.findViewById(R.id.norm_phone);
        EditText edit_addr = view.findViewById(R.id.edit_address);
        EditText edit_email = view.findViewById(R.id.edit_email_address);
        Button save_changes = view.findViewById(R.id.save_after_edit);



        //temporary button
        Button viewDb = view.findViewById(R.id.view_db_page);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                norm_phone.setVisibility(View.GONE);
                edit_profile.setVisibility(View.GONE);
                //display edittext and save button
                edit_phone.setVisibility(View.VISIBLE);
                edit_addr.setVisibility(View.VISIBLE);
                edit_email.setVisibility(View.VISIBLE);
                save_changes.setVisibility(View.VISIBLE);
            }
        });
        //onclick for save changes button
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Do not save change if invalid data is entered
                // Validate the data
                RegexValidator regexValidator = new RegexValidator();
                boolean isMobileValid = regexValidator.validMobile(edit_phone.getText().toString());
                boolean isEmailValid = regexValidator.validEmail(edit_email.getText().toString());
                String new_number = "+91 " + edit_phone.getText().toString();
                if (isEmailValid && isMobileValid) {

                    // TODO: Update the database before closing the edit option
                    FirebaseDatabase db = FirebaseDatabase.getInstance("https://projecta-8defc-default-rtdb.firebaseio.com/");


                    Users updated_user = new Users();
                    DatabaseReference reference = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    updated_user.setEmail(edit_email.getText().toString());
                    updated_user.setMobile_no(edit_phone.getText().toString());
                    updated_user.setAddress(edit_addr.getText().toString());
                    updated_user.setDefault_address(edit_addr.getText().toString());

                    reference.setValue(updated_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Data updated successfully!", Toast.LENGTH_SHORT).show();
                        }
                    });


                    // close save changes and edit option
                    edit_phone.setVisibility(View.GONE);
                    save_changes.setVisibility(View.GONE);
                    edit_addr.setVisibility(View.GONE);
                    edit_email.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Your changes have been saved", Toast.LENGTH_LONG).show();

                    // display textview and edit option
                    norm_phone.setVisibility(View.VISIBLE);
                    edit_profile.setVisibility(View.VISIBLE);

                    //update the number
                    norm_phone.setText(new_number);
                } else {
                    Toast.makeText(getContext(), "Invalid data entered, cannot save changes.", Toast.LENGTH_SHORT).show();
                }

                //update the database
                // TODO: add DB code
                // TODO: count number of times number has been changed
                // TODO: limit to 3 changes
                // TODO: Add option to change password
                // TODO: Password or  number
                // TODO: add cancel changes button
                // TODO: add related alerts
            }
        });

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });
        return view;
    }
}