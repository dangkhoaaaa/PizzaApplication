package com.example.pizzaapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.PizzaAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.PizzaModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.repository.PizzaRepository;
import com.example.pizzaapplication.viewmodel.PizzaViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PizzaFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private PizzaViewModel pizzaViewModel;
    private static final String TAG = "PizzaFragment";
    private ImageSlider imageSlider;
    private List<SlideModel> imageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewPizza);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pizzaAdapter = new PizzaAdapter();
        recyclerView.setAdapter(pizzaAdapter);

        // Initialize ImageSlider
        imageSlider = view.findViewById(R.id.imageSlideshow);
        imageList = new ArrayList<>();

        // Add images to the imageList with drawable resource IDs
        imageList.add(new SlideModel(R.drawable.pizza_banner_1, ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel(R.drawable.pizza_banner_2, ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel(R.drawable.pizza_banner_3, ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel(R.drawable.pizza_banner_4, ScaleTypes.CENTER_INSIDE));

        imageSlider.setImageList(imageList);

        // Initialize PizzaRepository and PizzaViewModel
        PizzaRepository pizzaRepository = new PizzaRepository(RetrofitClient.getApiService());
        pizzaViewModel = new PizzaViewModel(pizzaRepository);

        // Observe LiveData from PizzaViewModel
        LiveData<PizzaResponseModel> liveDataPizzas = pizzaViewModel.getPizzas();
        liveDataPizzas.observe(getViewLifecycleOwner(), new Observer<PizzaResponseModel>() {
            @Override
            public void onChanged(PizzaResponseModel pizzaResponseModel) {
                if (pizzaResponseModel != null) {
                    displayPizzas(pizzaResponseModel.getPizzas());
                    logPizzas(pizzaResponseModel.getPizzas());
                } else {
                    Log.d(TAG, "No pizzas received");
                }
            }
        });

        // Set item click listener
        pizzaAdapter.setOnItemClickListener(new PizzaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PizzaModel pizza) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra("pizza", pizza);
                startActivity(intent);
            }
        });

        return view;
    }

    private void displayPizzas(List<PizzaModel> pizzas) {
        pizzaAdapter.setPizzas(pizzas);
    }

    private void logPizzas(List<PizzaModel> pizzas) {
        for (PizzaModel pizza : pizzas) {
            Log.d(TAG, "Pizza: " + pizza.getName() + ", Price: " + pizza.getPrice());
        }
    }
}
