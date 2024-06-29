package com.example.pizzaapplication.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.pizzaapplication.R;
import com.example.pizzaapplication.share.DataLocalManager;
import com.example.pizzaapplication.utils.JwtUtils;

public class ChatActivity extends AppCompatActivity {

    private String[] storeIds = {"store1", "store2", "store3"};
    private static final String CHAT_PREFS = "ChatPrefs";
    private static final String USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Check if the token exists
        String token = DataLocalManager.getInstance().getToken();
        if (token == null) {
            // Redirect to login activity if no token is found
            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent the user from going back
            return;
        }

        // Extract user ID from token
        String userId = JwtUtils.getUserId(token);
        if (userId == null) {
            Toast.makeText(this, "Invalid token. Please log in again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //String userId = getSharedPreferences(CHAT_PREFS, Context.MODE_PRIVATE).getString(USER_ID, "defaultUser");

        ListView listViewStores = findViewById(R.id.listViewStores);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, storeIds);
        listViewStores.setAdapter(adapter);

        listViewStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String storeId = storeIds[position];
                Intent intent = new Intent(ChatActivity.this, StoreChatActivity.class);
                intent.putExtra("storeId", storeId);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
}
