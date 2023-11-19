package com.example.project_a;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button edit_profile = view.findViewById(R.id.editProfile);
        EditText edit_phone = view.findViewById(R.id.edit_phone);
        TextView norm_phone = view.findViewById(R.id.norm_phone);
        Button save_changes = view.findViewById(R.id.save_after_edit);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String old_number = norm_phone.getText().toString();

                norm_phone.setVisibility(View.GONE);
                edit_profile.setVisibility(View.GONE);
                //display edittext and save button
                edit_phone.setVisibility(View.VISIBLE);
                save_changes.setVisibility(View.VISIBLE);

            }
        });

        //onclick for save changes button
        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_number = "+91 " + edit_phone.getText().toString();
                // close save changes and edit option
                edit_phone.setVisibility(View.GONE);
                save_changes.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Your changes have been saved", Toast.LENGTH_LONG).show();

                // display textview and edit option
                norm_phone.setVisibility(View.VISIBLE);
                edit_profile.setVisibility(View.VISIBLE);

                //update the number
                norm_phone.setText(new_number);

                //update the database
                // TODO: add DB code
                // TODO: count number of times number has been changed
                // TODO: limit to 3 changes
                // TODO: Add option to change password
                // TODO: Password or  number
            }
        });

        return view;
    }
}