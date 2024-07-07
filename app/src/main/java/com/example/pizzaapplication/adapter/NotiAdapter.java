package com.example.pizzaapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.api.ApiService;
import com.example.pizzaapplication.data.api.RetrofitClient;
import com.example.pizzaapplication.data.model.Response.ApiResponse;
import com.example.pizzaapplication.utils.Utils;
import com.example.pizzaapplication.view.CartActivity;
import com.example.pizzaapplication.view.admin.NotificationActivity;
import com.example.pizzaapplication.view.admin.OrderNotiDetailsActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.ViewHolder>{

    Context context;
    ArrayList content, date, stats, orderId;
    Activity activity;

    public NotiAdapter(Activity activity,
                Context context,
                ArrayList content,
                ArrayList date,
                       ArrayList stats,
                       ArrayList orderId){
        this.activity = activity;
        this.context = context;
        this.content = content;
        this.date = date;
        this.stats = stats;
        this.orderId = orderId;
    }


    @NonNull
    @Override
    public NotiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.noti_row, parent, false);
        return new NotiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiAdapter.ViewHolder holder, int position) {

        holder.content_txt.setText(content.get(position).toString());
        holder.date_txt.setText(Utils.formatDateOfBirth(date.get(position).toString()));

        if (stats.get(position).equals("1")) {
            holder.notiLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.PaleTurquoise));
        } else {
            holder.notiLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.White));
        }

        holder.notiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                //split content after ","
                String data = content.get(adapterPosition).toString();
                String[] parts = data.split(", ");
                String name = parts[0];
                String address = parts[1];
                String phone = parts[2];
                //order is parts[3] to the end
                String order = "";
                for (int i = 3; i < parts.length; i++) {
                    order += parts[i];
                    if (i != parts.length - 1) {
                        order += ", ";
                    }
                }

                int id  = (int) orderId.get(adapterPosition);

                updateNotificationStatus(id, context);
                //pass data to the next activity
                Intent intent = new Intent(context, OrderNotiDetailsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("order", order);
                activity.startActivityForResult(intent,1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content_txt, date_txt;
        LinearLayout notiLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content_txt = itemView.findViewById(R.id.content_text);
            date_txt = itemView.findViewById(R.id.date_text);
            notiLayout = itemView.findViewById(R.id.notiLayout);


        }
    }

    private void updateNotificationStatus(int orderId, Context context) {
        // Initialize Retrofit
        // Make the API call
        ApiService apiService = RetrofitClient.getApiService();
        Call<ApiResponse<Integer>> call = apiService.updateNotificationStatus(orderId);


        call.enqueue(new Callback<ApiResponse<Integer>>() {
            @Override
            public void onResponse(Call<ApiResponse<Integer>> call, Response<ApiResponse<Integer>> response) {
                if (response.isSuccessful()) {
                    // Handle success
                    Toast.makeText(context, " Updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failure
                    Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Integer>> call, Throwable t) {
                // Handle error
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
