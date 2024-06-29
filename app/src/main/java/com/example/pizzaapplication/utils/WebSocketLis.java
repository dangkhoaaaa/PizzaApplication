package com.example.pizzaapplication.utils;

import android.util.Pair;
import com.example.pizzaapplication.viewmodel.ChatViewModel;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class WebSocketLis extends WebSocketListener {

    private ChatViewModel viewModel;
    private String userId;

    public WebSocketLis(ChatViewModel viewModel, String userId) {
        this.viewModel = viewModel;
        this.userId = userId;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        viewModel.setStatus(true);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        try {
            JSONObject json = new JSONObject(text);
            String senderId = json.getString("userId");
            String message = json.getString("message");

            if (!senderId.equals(userId)) {
                viewModel.setMessage(new Pair<>(false, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        viewModel.setStatus(false);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        // Handle failure
    }
}
