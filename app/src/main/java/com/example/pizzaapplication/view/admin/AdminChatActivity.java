package com.example.pizzaapplication.view.admin;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaapplication.R;
import com.example.pizzaapplication.adapter.MessageAdapter;
import com.example.pizzaapplication.utils.WebSocketLis;
import com.example.pizzaapplication.viewmodel.ChatViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class AdminChatActivity extends AppCompatActivity {

    private WebSocket webSocket;
    private OkHttpClient okHttpClient = new OkHttpClient();
    private WebSocketLis websocketListener;
    private ChatViewModel viewModel;

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<String> messageList;
    private EditText editTextMessage;
    private Button buttonSend;
    private String userId;
    private String userIdCustom;
    private String storeId = "store1"; // Admin's storeId
    private String channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat);

        recyclerView = findViewById(R.id.recyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        userId = "admin";
        userIdCustom = getIntent().getStringExtra("userIdCustom");
        //channel="store1_5";
        channel = storeId + "_" + 5;

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        websocketListener = new WebSocketLis(viewModel, userId);

        viewModel.getSocketStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean connected) {
                runOnUiThread(() -> {
                    if (connected) {
                        // messageList.add("Connected");
                    } else {
                        // messageList.add("Disconnected");
                    }
                    messageAdapter.notifyDataSetChanged();
                });
            }
        });

        viewModel.getMessage().observe(this, new Observer<Pair<Boolean, String>>() {
            @Override
            public void onChanged(Pair<Boolean, String> messagePair) {
                runOnUiThread(() -> {
                    String text = messagePair.first ? "You: " : "Customer: ";
                    text += messagePair.second;
                    messageList.add(text);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);
                });
            }
        });

        String webSocketUrl = "wss://free.blr2.piesocket.com/v3/" + channel + "?api_key=d1kQN14ZePLsfkAx5Qh6RZ8Zh8mbZJhWdcD46Q5s&notify_self=1";
        Request request = new Request.Builder().url(webSocketUrl).build();
        webSocket = okHttpClient.newWebSocket(request, websocketListener);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                if (!message.isEmpty()) {
                    try {
                        JSONObject json = new JSONObject();
                        json.put("userId", userId);
                        json.put("message", message);
                        webSocket.send(json.toString());
                        viewModel.setMessage(new Pair<>(true, message));
                        editTextMessage.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
