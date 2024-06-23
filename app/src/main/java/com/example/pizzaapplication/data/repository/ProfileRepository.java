package com.example.pizzaapplication.data.repository;

import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.model.Request.ProfileRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.ProfileResponseModel;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {
    private ApiService apiService;

    public ProfileRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getProfile(Callback<ProfileResponseModel> callback) {
        Call<ProfileResponseModel> call = apiService.getProfile();
        call.enqueue(callback);
    }

    public void updateProfile(ProfileRequestModel profileRequestModel, MultipartBody.Part profilePic, Callback<ApiResponse> callback) {
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(profileRequestModel.getId()));
        RequestBody firstName = RequestBody.create(MediaType.parse("multipart/form-data"), profileRequestModel.getFirstName());
        RequestBody lastName = RequestBody.create(MediaType.parse("multipart/form-data"), profileRequestModel.getLastName());
        RequestBody dateOfBirth = RequestBody.create(MediaType.parse("multipart/form-data"), profileRequestModel.getDateOfBirth());
        RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), profileRequestModel.getAddress());
        RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), profileRequestModel.getPhone());

        Call<ApiResponse> call = apiService.updateProfile(id, firstName, lastName, dateOfBirth, address, phone, profilePic);
        call.enqueue(callback);
    }


}
