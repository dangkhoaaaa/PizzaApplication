package com.example.pizzaapplication.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;



import com.example.pizzaapplication.data.model.User;
import com.example.pizzaapplication.data.repository.MainRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainRepository mainRepository;

    public MainViewModel(MainRepository mainRepository) {
        users = new MutableLiveData<>();
        this.mainRepository = mainRepository;
//        fetchUsers();
    }

//    public LiveData<List<User>> getUsers() {
//        return users;
//    }
//
//    private void fetchUsers() {
//        mainRepository.getUsers(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                if (response.isSuccessful()) {
//                    users.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                // Handle failure
//            }
//        });
//    }
//
//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        compositeDisposable.clear();
//    }
}


