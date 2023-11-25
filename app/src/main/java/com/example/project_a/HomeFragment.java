package com.example.project_a;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationRequest;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.Manifest;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private static final int LOCATION_PERMISSION_CODE = 100;
    EditText source_location;
    String source;

    private FusedLocationProviderClient fusedLocationClient;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // importing all the widgets
        EditText date = view.findViewById(R.id.travel_date);
        EditText dest_loc = view.findViewById(R.id.dest_loc);
        EditText num_people = view.findViewById(R.id.num_passengers);

        // Database helper code
        DBHelper dbHelper = new DBHelper(view.getContext());
        Button select_a_vehi = view.findViewById(R.id.select_vehicle_submit);
        LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());


        // validate the data
        // TODO: 1. validate date
        RegexValidator regexValidator = new RegexValidator();


        // Add intent on onclick listener
        select_a_vehi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_select_vehi = new Intent(getContext(), AvailableVehicles.class);

                boolean isValidDate = regexValidator.validDate(date.getText().toString());
                if (isValidDate) {
                    Toast.makeText(getContext(), "Valid date.", Toast.LENGTH_SHORT).show();

                    //TODO: Database connection here
                    startActivity(go_to_select_vehi);
                } else {
                    Toast.makeText(getContext(), "" + isValidDate, Toast.LENGTH_SHORT).show();
                }


                // add to database
//                long inserted = dbHelper.addRental("V001", "23/11/2023", source, "yelahanka", 3);
//                if (inserted < 1){
//                    Toast.makeText(getContext(), "Cannot Insert", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getContext(), "Inserted Successfully", Toast.LENGTH_SHORT).show();
//                }
                // startActivity(go_to_select_vehi);
            }
        });

        // Requesting permissions
        String[] perms = {"android.permission.FINE_LOCATION"};
        int permCode = 200;


        // Check if permission is not granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            // Permission already granted, proceed to get location
            requestSingleLocationUpdate();

        }

        return view;
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get location
                requestSingleLocationUpdate();
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
                showToast("Location permission denied");
            }
        }
    }

    private void requestSingleLocationUpdate() {
        // Check for permission before requesting location updates
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // Request the last known location
            fusedLocationClient.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                public void onSuccess(Location location) {
                    if (location != null) {
                        // Handle the location and call the method to get location name
                        getAndDisplayLocationName(location.getLatitude(), location.getLongitude());
                    } else {
//                        getAndDisplayLocationName(location.getLatitude(), location.getLongitude());
                        showToast("Last known location not available");
                    }
                }
            });
        }
    }

    private void getAndDisplayLocationName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
        source_location = getView().findViewById(R.id.source_loc);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();

                String locationName = address + ", " + city + ", " + country;
                source_location.setText(locationName);
                source = locationName;
//                showToast("Location: " + locationName);
            } else {
                showToast("Location not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error getting location: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }


    // TODO: Get location permission from the user, or manually choose a location
    // get location only if the field is emtpy
}