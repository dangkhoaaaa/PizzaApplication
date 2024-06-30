package com.example.pizzaapplication.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.ProfileRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.repository.ProfileRepository;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.utils.Utils;
import com.example.pizzaapplication.viewmodel.ProfileViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Response;

public class ProfileFragment extends Fragment {
    private EditText tvFullName, tvDoB, tvAddress, tvPhone, tvMail;
    private Button buttonSettings, changePassword, btnUpdateProfile, btnOrderHistory, btnLogout;
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
        tvDoB = view.findViewById(R.id.tvDoB);
        tvAddress = view.findViewById(R.id.tvAdress);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvMail = view.findViewById(R.id.tvMail);
//        buttonSettings = view.findViewById(R.id.buttonSettings);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        btnOrderHistory = view.findViewById(R.id.btnOrderHistory);
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
                    enableEditing();

                    // Set hints for other EditTexts
                } else {
                    disableEditing();
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
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle order history button click
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear user data
                DataLocalManager.removeToken();
                DataLocalManager.setUserId("");

                // Navigate to login activity
                Intent intent = new Intent(getActivity(), InitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear back stack
                startActivity(intent);
            }
        });
        return view;
    }

    private void enableEditing() {
        tvFullName.setEnabled(true);
        tvDoB.setEnabled(true);
        tvAddress.setEnabled(true);
        tvPhone.setEnabled(true);
        tvFullName.setBackgroundTintList(null);
        tvDoB.setBackgroundTintList(null);
        tvAddress.setBackgroundTintList(null);
        tvPhone.setBackgroundTintList(null);
        Drawable background = getResources().getDrawable(R.drawable.btn_background_xanh);
        btnUpdateProfile.setBackground(background);
        tvFullName.setHint("Enter new first name");
    }

    private void disableEditing() {
        tvFullName.setEnabled(false);
        tvDoB.setEnabled(false);
        tvAddress.setEnabled(false);
        tvPhone.setEnabled(false);
        Drawable background = getResources().getDrawable(R.drawable.btn_background_1);
        btnUpdateProfile.setBackground(background);
        int white = getResources().getColor(R.color.White); // Assuming "white" is defined in colors.xml
        ColorStateList whiteTintList = ColorStateList.valueOf(white);
        tvFullName.setBackgroundTintList(whiteTintList);
        tvDoB.setBackgroundTintList(whiteTintList);
        tvAddress.setBackgroundTintList(whiteTintList);
        tvPhone.setBackgroundTintList(whiteTintList);
    }

    private void displayProfile(ProfileResponseModel profileResponseModel) {
        tvFullName.setText(profileResponseModel.getName());
        // Convert date of birth to desired format

        String dateOfBirth = profileResponseModel.getDateOfBirth();
        String formattedDoB = Utils.formatDateOfBirth(dateOfBirth);
        tvDoB.setText(formattedDoB);
        tvAddress.setText(profileResponseModel.getAddress());
        tvPhone.setText(profileResponseModel.getPhone());
        tvMail.setText(profileResponseModel.getEmail());
    }

//    private String formatDateOfBirth(String dateOfBirth) {
//        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
//            return ""; // Handle empty date
//        }
//
//        try {
//            // Parse the date from the API response format (assuming ISO 8601)
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            Date date = inputFormat.parse(dateOfBirth);
//
//            // Format the date to the desired output format
//            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
//            return outputFormat.format(date);
//        } catch (ParseException e) {
//            Log.e(TAG, "Error parsing date of birth: " + e.getMessage());
//            return dateOfBirth; // Return original value if parsing fails
//        }
//    }


    private void logProfile(ProfileResponseModel profileResponseModel) {
            Log.d(TAG, "Name: " + profileResponseModel.getName());
    }

    // Method to update profile information on server (implement your logic here)
    private void updateProfileInformation() {
        int userId = Integer.parseInt(DataLocalManager.getUserId());
        String newFullName = tvFullName.getText().toString();
        String newDoB = tvDoB.getText().toString();
        String newAddress = tvAddress.getText().toString();
        String newPhone = tvPhone.getText().toString();

        if (!Utils.isValidPhoneNumber(newPhone)) {
            Toast.makeText(requireContext(), "Invalid phone number format. Please enter a 10-digit number starting with '0'", Toast.LENGTH_SHORT).show();
            return;
        }

        ProfileResponseModel currentProfile = profileViewModel.getProfileData().getValue();
        if (currentProfile != null &&
                currentProfile.getName().equals(newFullName) &&
                Utils.formatDateOfBirth(currentProfile.getDateOfBirth()).equals(newDoB) &&
                currentProfile.getAddress().equals(newAddress) &&
                currentProfile.getPhone().equals(newPhone)) {
            Toast.makeText(requireContext(), "No changes detected", Toast.LENGTH_SHORT).show();
            return;
        }

        ProfileRequestModel profileRequestModel = new ProfileRequestModel(
                userId, newFullName, newDoB, newAddress, newPhone, null
        );

        MultipartBody.Part profilePic = null;

        profileViewModel.updateProfile(profileRequestModel, profilePic);
        profileViewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), updateSuccess -> {
            if (updateSuccess != null && updateSuccess) {
                showSuccessToast();
                updateUiElementsWithNewInformation(newFullName, newDoB, newAddress, newPhone);
            } else {
                Toast.makeText(requireContext(), "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showSuccessToast() {
        String successMessage = getString(R.string.profile_update_success); // Use string resource for localization
        Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidPhoneNumber(String phone) {
        // Improved phone number validation using regex
        String phoneRegex = "^0[0-9]{9}$";
        return phone.matches(phoneRegex);
    }


    private void updateUiElementsWithNewInformation(String newFullName,  String newDoB, String newAddress, String newPhone) {
        // Update UI elements with new information as in previous code
        tvFullName.setText(newFullName);
        tvDoB.setText(newDoB);
        tvAddress.setText(newAddress);
        tvPhone.setText(newPhone);
    }


}
