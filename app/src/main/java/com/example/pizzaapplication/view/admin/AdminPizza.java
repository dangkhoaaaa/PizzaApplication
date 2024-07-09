package com.example.pizzaapplication.view.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.AdminPizzaAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.PizzaCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.PizzaUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.PizzaModel;
import com.example.pizzaapplication.data.model.Response.PizzaResponseModel;
import com.example.pizzaapplication.data.repository.PizzaRepository;
import com.example.pizzaapplication.viewmodel.PizzaViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminPizza extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private AdminPizzaAdapter pizzaAdapter;
    private PizzaViewModel pizzaViewModel;

    private ImageView selectedImageView;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pizza);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAdminPizza);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaAdapter = new AdminPizzaAdapter();
        recyclerView.setAdapter(pizzaAdapter);

        // Initialize PizzaRepository and PizzaViewModel
        PizzaRepository pizzaRepository = new PizzaRepository(RetrofitClient.getApiService());
        pizzaViewModel = new PizzaViewModel(pizzaRepository);

        // Observe LiveData from PizzaViewModel
        LiveData<PizzaResponseModel> liveDataPizzas = pizzaViewModel.getPizzas();
        liveDataPizzas.observe(this, new Observer<PizzaResponseModel>() {
            @Override
            public void onChanged(PizzaResponseModel pizzaResponseModel) {
                if (pizzaResponseModel != null) {
                    displayPizzas(pizzaResponseModel.getPizzas());
                } else {
                    Toast.makeText(AdminPizza.this, "No pizzas received", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set item click listener for updating pizza
        pizzaAdapter.setOnItemClickListener(new AdminPizzaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PizzaModel pizza) {
                showUpdateDeleteDialog(pizza);
            }
        });

        // Add pizza button
        findViewById(R.id.btnAddPizza).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPizzaDialog();
            }
        });
    }

    private void displayPizzas(List<PizzaModel> pizzas) {
        pizzaAdapter.setPizzas(pizzas);
    }

    private void showUpdateDeleteDialog(PizzaModel pizza) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update/Delete Pizza");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_pizza, (ViewGroup) getWindow().getDecorView().getRootView(), false);
        final EditText inputName = viewInflated.findViewById(R.id.inputPizzaName);
        final EditText inputDescription = viewInflated.findViewById(R.id.inputPizzaDescription);
        final EditText inputPrice = viewInflated.findViewById(R.id.inputPizzaPrice);
        selectedImageView = viewInflated.findViewById(R.id.imageViewPizza);

        inputName.setText(pizza.getName());
        inputDescription.setText(pizza.getDescription());
        inputPrice.setText(String.valueOf(pizza.getPrice()));
        Picasso.get().load(pizza.getImage()).into(selectedImageView);

        selectedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

        builder.setView(viewInflated);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Upload the image first if a new one is selected
                if (selectedImageUri != null) {
                    uploadImage(selectedImageUri, new OnImageUploadListener() {
                        @Override
                        public void onImageUploaded(String imageUrl) {
                            // Update pizza with new image URL
                            updatePizza(pizza, inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), imageUrl);
                        }
                    });
                } else {
                    // Update pizza with existing image URL
                    updatePizza(pizza, inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), pizza.getImage());
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete pizza
//                pizzaViewModel.deletePizza(pizza.getPizzaId()).observe(AdminPizza.this, new Observer<ApiResponse>() {
//                    @Override
//                    public void onChanged(ApiResponse apiResponse) {
//                        if (apiResponse != null) {
//                            Toast.makeText(AdminPizza.this, "Pizza deleted", Toast.LENGTH_SHORT).show();
//                            pizzaViewModel.getPizzas();
//                        } else {
//                            Toast.makeText(AdminPizza.this, "Failed to delete pizza", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void updatePizza(PizzaModel pizza, String name, String description, double price, String imageUrl) {
        PizzaUpdateRequestModel updatePizza = new PizzaUpdateRequestModel(
                pizza.getPizzaId(),
                name,
                description,
                price,
                imageUrl
        );

        pizzaViewModel.updatePizza(updatePizza).observe(AdminPizza.this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    Toast.makeText(AdminPizza.this, "Pizza updated", Toast.LENGTH_SHORT).show();
                    pizzaViewModel.getPizzas();
                    reloadActivity();
                } else {
                    Toast.makeText(AdminPizza.this, "Failed to update pizza", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAddPizzaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Pizza");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_pizza, (ViewGroup) getWindow().getDecorView().getRootView(), false);
        final EditText inputName = viewInflated.findViewById(R.id.inputPizzaName);
        final EditText inputDescription = viewInflated.findViewById(R.id.inputPizzaDescription);
        final EditText inputPrice = viewInflated.findViewById(R.id.inputPizzaPrice);
        selectedImageView = viewInflated.findViewById(R.id.imageViewPizza);

        selectedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageSelector();
            }
        });

        builder.setView(viewInflated);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Upload the image first
                if (selectedImageUri != null) {
                    uploadImage(selectedImageUri, new OnImageUploadListener() {
                        @Override
                        public void onImageUploaded(String imageUrl) {
                            // Add pizza with new image URL
                            addPizza(inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), imageUrl);
                        }
                    });
                } else {
                    // Add pizza with no image URL
                    addPizza(inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), "");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addPizza(String name, String description, double price, String imageUrl) {
        PizzaCreateRequestModel newPizza = new PizzaCreateRequestModel(
                name,
                description,
                price,
                imageUrl
        );

        pizzaViewModel.createPizza(newPizza).observe(AdminPizza.this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    Toast.makeText(AdminPizza.this, "Pizza added", Toast.LENGTH_SHORT).show();
                    pizzaViewModel.getPizzas();
                    reloadActivity();
                } else {
                    Toast.makeText(AdminPizza.this, "Failed to add pizza", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                selectedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Uri imageUri, OnImageUploadListener listener) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        Date date = new Date();
        String imageName = formatter.format(date);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + imageName);

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressDialog.dismiss();
                        String imageUrl = uri.toString();
                        listener.onImageUploaded(imageUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminPizza.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AdminPizza.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface OnImageUploadListener {
        void onImageUploaded(String imageUrl);
    }
    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
