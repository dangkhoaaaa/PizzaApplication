package com.example.pizzaapplication.data.api;



import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//https://localhost:7128/api/pizzas?current-page=1&page-size=3&sort-by-price=true&descending=true
   //https://666187b263e6a0189fea53d8.mockapi.io
    //http://192.168.1.67:3348/api/pizzas?current-page=1&page-size=3&sort-by-price=true&descending=true
    private static final String BASE_URL = "http://192.168.61.118:3348/api/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}

