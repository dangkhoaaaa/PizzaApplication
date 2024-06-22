package com.example.pizzaapplication.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.pizzaapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import android.content.pm.PackageManager;
import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private SearchView searchView;
    private MarkerOptions userMarkerOptions; // Marker options for user's location

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);

        searchView = view.findViewById(R.id.search_view);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        // Check for location permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Enable the My Location layer
        gMap.setMyLocationEnabled(true);

        // Get current location and add markers
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                    // Save user's location for later use
                    userMarkerOptions = new MarkerOptions()
                            .position(currentLocation)
                            .title("Your Location")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)); // Blue marker for user's location

                    gMap.addMarker(userMarkerOptions); // Add marker for user's location
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

                    // Add pizza shop markers
                    addPizzaShopMarkers(currentLocation);

                    // For now, we simply log the current location
                    Log.d("MapFragment", "Current location: " + currentLocation);
                }
            }
        });
    }

    private void addPizzaShopMarkers(LatLng currentLocation) {
        LatLng pizzaShop1 = new LatLng(10.875345048434951, 106.80072339507166);
        LatLng pizzaShop2 = new LatLng(10.876345048434951, 106.81072339507166);
        LatLng pizzaShop3 = new LatLng(10.877345048434951, 106.82072339507166);

        // Load the custom marker icon
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pizza_logo);

        // Resize the bitmap
        int width = 100; // Width in pixels
        int height = 100; // Height in pixels
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);

        // Create a BitmapDescriptor from the resized bitmap
        BitmapDescriptor customIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

        gMap.addMarker(new MarkerOptions().position(pizzaShop1).title("Pizza Shop 1").icon(customIcon));
        gMap.addMarker(new MarkerOptions().position(pizzaShop2).title("Pizza Shop 2").icon(customIcon));
        gMap.addMarker(new MarkerOptions().position(pizzaShop3).title("Pizza Shop 3").icon(customIcon));
    }

    private void searchLocation(String locationName) {
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                gMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                Log.e("MapFragment", "Location not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MapFragment", "Geocoder IOException: " + e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(gMap);
            }
        }
    }
}
