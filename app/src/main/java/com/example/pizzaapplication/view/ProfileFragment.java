package com.example.pizzaapplication.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.repository.ProfileRepository;
import com.example.pizzaapplication.viewmodel.ProfileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private EditText tvFullName, tvMail, tvDoB, tvAddress, tvPhone;
    private Button buttonSettings, changePassword, btnUpdateProfile, buttonNotifications, btnLogout;
    private CircleImageView profileImageView;
    private ProfileViewModel profileViewModel;
    private boolean editingEnabled = false;
    private static final String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        tvFullName = view.findViewById(R.id.tvFullName);
        tvMail = view.findViewById(R.id.tvMail);
        tvDoB = view.findViewById(R.id.tvDoB);
        tvAddress = view.findViewById(R.id.tvAdress);
        tvPhone = view.findViewById(R.id.tvPhone);
//        buttonSettings = view.findViewById(R.id.buttonSettings);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
//        buttonNotifications = view.findViewById(R.id.buttonNotifications);
        btnLogout = view.findViewById(R.id.btnLogout);
//        profileImageView = view.findViewById(R.id.profileImageView);

        // Initialize ProfileViewModel
        ProfileRepository profileRepository = new ProfileRepository(RetrofitClient.getApiService());
        profileViewModel = new ProfileViewModel(profileRepository);

        // Observe livedata profile data from ViewModel
        LiveData<ProfileResponseModel> liveDataProfile = profileViewModel.getProfileData();
        liveDataProfile.observe(getViewLifecycleOwner(), new Observer<ProfileResponseModel>() {
            @Override
            public void onChanged(ProfileResponseModel profileResponseModel) {
                if (profileResponseModel != null) {
                    // Update UI elements with profile information
                    displayProfile(profileResponseModel);
                    logProfile(profileResponseModel);

                } else {
                    // Handle case where profile data is null
                    Log.d(TAG, "No profile data received");
                }
            }
        });

        // Set button click listeners

//        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle update profile button click
//                Intent intent = new Intent(getActivity(), updateProfile.class);
//                startActivity(intent);
//            }
//        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editingEnabled = !editingEnabled;

                if (editingEnabled) {
                    tvFullName.setEnabled(true);
                    tvMail.setEnabled(true);
                    tvDoB.setEnabled(true);
                    tvAddress.setEnabled(true);
                    tvPhone.setEnabled(true);
                    tvFullName.setBackgroundTintList(null);
                    tvMail.setBackgroundTintList(null);
                    tvDoB.setBackgroundTintList(null);
                    tvAddress.setBackgroundTintList(null);
                    tvPhone.setBackgroundTintList(null);
                    Drawable background = getResources().getDrawable(R.drawable.btn_background_xanh);
                    btnUpdateProfile.setBackground(background);
                    tvFullName.setHint("Enter new first name");

                    // Set hints for other EditTexts
                } else {
                    tvFullName.setEnabled(false);
                    tvMail.setEnabled(false);
                    tvDoB.setEnabled(false);
                    tvAddress.setEnabled(false);
                    tvPhone.setEnabled(false);
                    Drawable background = getResources().getDrawable(R.drawable.btn_background_1);
                    btnUpdateProfile.setBackground(background);
                    // Get white color from resources
                    int white = getResources().getColor(R.color.White); // Assuming "white" is defined in colors.xml

                    // Create ColorStateList with white as default
                    ColorStateList whiteTintList = ColorStateList.valueOf(white);

                    // Set white tint for all TextViews
                    tvFullName.setBackgroundTintList(whiteTintList);
                    tvMail.setBackgroundTintList(whiteTintList);
                    tvDoB.setBackgroundTintList(whiteTintList);
                    tvAddress.setBackgroundTintList(whiteTintList);
                    tvPhone.setBackgroundTintList(whiteTintList);

                    // Update profile information on server (optional)
                    updateProfileInformation();
                }
            }
        });

//        buttonSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle settings button click
//                Intent intent = new Intent(getActivity(), SettingsActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//        buttonNotifications.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle notification button click
//                Intent intent = new Intent(getActivity(), NotificationSettingsActivity.class);
//                startActivity(intent);
//            }
//        });

//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Clear user data
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//
//                // Navigate to login activity
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//
//                // Optionally, finish the current activity to prevent back navigation
//                getActivity().finish();
//            }
//        });
        return view;
    }

    private void displayProfile(ProfileResponseModel profileResponseModel) {
        tvFullName.setText(profileResponseModel.getFirstName() + " " + profileResponseModel.getLastName());
        tvMail.setText(profileResponseModel.getEmail());
        // Convert date of birth to desired format
        String dateOfBirth = profileResponseModel.getDateOfBirth();
        String formattedDoB = formatDateOfBirth(dateOfBirth);
        tvDoB.setText(formattedDoB);
        tvAddress.setText(profileResponseModel.getAddress());
        tvPhone.setText(profileResponseModel.getPhone());
    }

    private String formatDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return ""; // Handle empty date
        }

        try {
            // Parse the date from the API response format (assuming ISO 8601)
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = inputFormat.parse(dateOfBirth);

            // Format the date to the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date of birth: " + e.getMessage());
            return dateOfBirth; // Return original value if parsing fails
        }
    }


    private void logProfile(ProfileResponseModel profileResponseModel) {
            Log.d(TAG, "Name: " + profileResponseModel.getFirstName() + ", lastname: " + profileResponseModel.getLastName());
    }

    // Method to update profile information on server (implement your logic here)
    private void updateProfileInformation() {
        String newFullName = tvFullName.getText().toString();
        String newEmail = tvMail.getText().toString();
        String newDoB = tvDoB.getText().toString();
        String newAddress = tvAddress.getText().toString();
        String newPhone = tvPhone.getText().toString();

        // Make API call to update profile using new information
        // ...

        // After successful update, update UI elements with new information
        tvFullName.setText(newFullName);
        tvMail.setText(newEmail);
        tvDoB.setText(newDoB);
        tvAddress.setText(newAddress);
        tvPhone.setText(newPhone);
    }
}
