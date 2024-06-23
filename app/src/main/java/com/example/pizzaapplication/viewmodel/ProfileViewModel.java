package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Request.ProfileRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.repository.ProfileRepository;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ProfileResponseModel> profileData;
    private MutableLiveData<Boolean> updateSuccess;
    private ProfileRepository profileRepository;

    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileData = new MutableLiveData<>();
        this.updateSuccess = new MutableLiveData<>();
        this.profileRepository = profileRepository;
        fetchProfile();
    }

    public MutableLiveData<ProfileResponseModel> getProfileData() {
        return profileData;
    }

    public MutableLiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }


    private void fetchProfile() {
        profileRepository.getProfile(new Callback<ProfileResponseModel>() {
            @Override
            public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    profileData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponseModel> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void updateProfile(ProfileRequestModel profileRequestModel, MultipartBody.Part profilePic) {
        profileRepository.updateProfile(profileRequestModel, profilePic, new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    updateSuccess.setValue(true); // Update thành công
                } else {
                    updateSuccess.setValue(false); // Update thất bại
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                updateSuccess.setValue(false); // Update thất bại
            }
        });
    }

}
