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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.AdminDrinkAdapter;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Request.DrinkCreateRequestModel;
import com.example.pizzaapplication.data.model.Request.DrinkUpdateRequestModel;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.data.model.Response.DrinkModel;
import com.example.pizzaapplication.data.model.Response.DrinkResponseModel;
import com.example.pizzaapplication.data.repository.DrinkRepository;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.viewmodel.DrinkViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminDrinkActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private RecyclerView recyclerView;
    private AdminDrinkAdapter drinkAdapter;
    private DrinkViewModel drinkViewModel;

    private ImageView selectedImageView;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drink);

        recyclerView = findViewById(R.id.recyclerViewAdminDrink);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drinkAdapter = new AdminDrinkAdapter();
        recyclerView.setAdapter(drinkAdapter);
        String token = DataLocalManager.getInstance().getToken();
        if (token == null) {
            // Redirect to login activity if no token is found

        }
        DrinkRepository drinkRepository = new DrinkRepository(RetrofitClient.getApiService());
        drinkViewModel = new DrinkViewModel(drinkRepository, token);

        loadDrinks();

        drinkAdapter.setOnItemClickListener(new AdminDrinkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DrinkModel drink) {
                showUpdateDeleteDialog(drink);
            }
        });

        findViewById(R.id.btnAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDrinkDialog();
            }
        });
    }

    private void loadDrinks() {
        LiveData<DrinkResponseModel> liveDataDrinks = drinkViewModel.getDrinks();
        liveDataDrinks.observe(this, new Observer<DrinkResponseModel>() {
            @Override
            public void onChanged(DrinkResponseModel drinkResponseModel) {
                if (drinkResponseModel != null) {
                    displayDrinks(drinkResponseModel.getDrinks());
                } else {
                    Toast.makeText(AdminDrinkActivity.this, "No drinks received", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayDrinks(List<DrinkModel> drinks) {
        drinkAdapter.setDrinks(drinks);
    }

    private void showUpdateDeleteDialog(DrinkModel drink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update/Delete Drink");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_update_drink, null);
        final EditText inputName = viewInflated.findViewById(R.id.inputDrinkName);
        final EditText inputDescription = viewInflated.findViewById(R.id.inputDrinkDescription);
        final EditText inputPrice = viewInflated.findViewById(R.id.inputDrinkPrice);
        selectedImageView = viewInflated.findViewById(R.id.imageViewDrink);

        inputName.setText(drink.getName());
        inputDescription.setText(drink.getDescription());
        inputPrice.setText(String.valueOf(drink.getPrice()));
        Picasso.get().load(drink.getImage()).into(selectedImageView);

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
                            // Update drink with new image URL
                            updateDrink(drink, inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), imageUrl);
                        }
                    });
                } else {
                    // Update drink with existing image URL
                    updateDrink(drink, inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), drink.getImage());
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete drink
//                drinkViewModel.deleteDrink(drink.getId()).observe(AdminDrinkActivity.this, new Observer<ApiResponse>() {
//                    @Override
//                    public void onChanged(ApiResponse apiResponse) {
//                        if (apiResponse != null) {
//                            Toast.makeText(AdminDrinkActivity.this, "Drink deleted", Toast.LENGTH_SHORT).show();
//                            reloadActivity(); // Reload the activity after deleting
//                        } else {
//                            Toast.makeText(AdminDrinkActivity.this, "Failed to delete drink", Toast.LENGTH_SHORT).show();
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

    private void updateDrink(DrinkModel drink, String name, String description, double price, String imageUrl) {
        DrinkUpdateRequestModel updateDrink = new DrinkUpdateRequestModel(
                drink.getId(),
                name,
                description,
                price,
                imageUrl
        );

        drinkViewModel.updateDrink(updateDrink).observe(AdminDrinkActivity.this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    Toast.makeText(AdminDrinkActivity.this, "Drink updated", Toast.LENGTH_SHORT).show();
                    reloadActivity(); // Reload the activity after updating
                } else {
                    Toast.makeText(AdminDrinkActivity.this, "Failed to update drink", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAddDrinkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Drink");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_add_drink, null);
        final EditText inputName = viewInflated.findViewById(R.id.inputDrinkName);
        final EditText inputDescription = viewInflated.findViewById(R.id.inputDrinkDescription);
        final EditText inputPrice = viewInflated.findViewById(R.id.inputDrinkPrice);
        selectedImageView = viewInflated.findViewById(R.id.imageViewDrink);

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
                            // Add drink with new image URL
                            addDrink(inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), imageUrl);
                        }
                    });
                } else {
                    // Add drink with no image URL
                    addDrink(inputName.getText().toString(), inputDescription.getText().toString(), Double.parseDouble(inputPrice.getText().toString()), "");
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

    private void addDrink(String name, String description, double price, String imageUrl) {
        DrinkCreateRequestModel newDrink = new DrinkCreateRequestModel(
                name,
                description,
                price,
                imageUrl
        );

        drinkViewModel.createDrink(newDrink).observe(AdminDrinkActivity.this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    Toast.makeText(AdminDrinkActivity.this, "Drink added", Toast.LENGTH_SHORT).show();
                    reloadActivity(); // Reload the activity after adding
                } else {
                    Toast.makeText(AdminDrinkActivity.this, "Failed to add drink", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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

    // Method to upload the image to Firebase Storage
    private void uploadImage(Uri imageUri, final OnImageUploadListener listener) {
        if (imageUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference ref = storageReference.child("images/" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    listener.onImageUploaded(downloadUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminDrinkActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    // Listener interface for image upload
    private interface OnImageUploadListener {
        void onImageUploaded(String imageUrl);
    }

    // Method to reload the activity
    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
