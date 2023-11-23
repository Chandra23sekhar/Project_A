package com.example.project_a;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

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
            }
        });

        //temp method
        viewDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showDB = new Intent(getContext(), DatabaseManipulation.class);
                startActivity(showDB);
            }
        });

        return view;
    }
}