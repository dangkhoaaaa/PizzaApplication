package com.example.pizzaapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;
import com.example.pizzaapplication.data.repository.ProfileRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ProfileResponseModel> profileData;
    private ProfileRepository profileRepository;

    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileData = new MutableLiveData<>();
        this.profileRepository = profileRepository;
        fetchProfile();
    }

    public MutableLiveData<ProfileResponseModel> getProfileData() {
        return profileData;
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
}
