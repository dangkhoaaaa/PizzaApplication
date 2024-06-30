package com.example.pizzaapplication.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<String> messageList;

    public MessageAdapter(List<String> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String message = messageList.get(position);
        holder.textViewMessage.setText(message);
        // Đặt lại căn chỉnh dựa trên điều kiện của tin nhắn (của bạn hay của người khác)
        if (message.startsWith("You:")) {
            // Căn phải nếu là tin nhắn của bạn
            holder.textViewMessage.setGravity(Gravity.END);
        } else {
            // Căn trái nếu là tin nhắn của người khác
            holder.textViewMessage.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
