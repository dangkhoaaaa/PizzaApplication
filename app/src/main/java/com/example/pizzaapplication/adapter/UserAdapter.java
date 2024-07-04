package com.example.pizzaapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.data.model.Response.UsersModel;
import com.example.pizzaapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<UsersModel> userList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(UsersModel user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setUsers(List<UsersModel> users) {
        for (UsersModel user : users) {
            user.setVerifiedAt(Utils.formatDateOfBirth(user.getVerifiedAt()));
        }
        this.userList = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_HEADER) {
            // No binding required for header
        } else {
            UsersModel user = userList.get(position - 1); // Adjust position for header
            ((UserViewHolder) holder).bind(user, onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size() + 1; // Add one for header
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView verifiedAtTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            verifiedAtTextView = itemView.findViewById(R.id.verifiedAtTextView);
        }

        public void bind(final UsersModel user, final OnItemClickListener listener) {
            nameTextView.setText(user.getName());
            emailTextView.setText(user.getEmail());
            verifiedAtTextView.setText(user.getVerifiedAt());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(user);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}