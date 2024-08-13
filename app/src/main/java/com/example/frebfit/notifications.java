package com.example.frebfit;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class notifications extends AppCompatActivity {

    private static final String PREFS_NAME = "notifications_prefs";
    private static final String NOTIFICATIONS_KEY = "notifications";

    private RecyclerView notificationsRecyclerView;
    private TextView noNotificationsText;
    private NotificationsAdapter notificationsAdapter;
    private List<NotificationItem> notifications;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.notification_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notificationsRecyclerView = findViewById(R.id.notifications_recycler_view);
        noNotificationsText = findViewById(R.id.no_notifications_text);
        Button clearAllButton = findViewById(R.id.clear_all_button);

        notifications = getNotifications();

        notificationsAdapter = new NotificationsAdapter(this, notifications, position -> {
            notifications.remove(position);
            notificationsAdapter.notifyItemRemoved(position);
            saveNotifications(notifications);
            checkIfNoNotifications();
        });

        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationsRecyclerView.setAdapter(notificationsAdapter);

        clearAllButton.setOnClickListener(v -> {
            notifications.clear();
            notificationsAdapter.notifyDataSetChanged();
            saveNotifications(notifications);
            checkIfNoNotifications();
        });

        checkIfNoNotifications();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Notification");
        }
    }

    private List<NotificationItem> getNotifications() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> notificationsSet = sharedPreferences.getStringSet(NOTIFICATIONS_KEY, new HashSet<>());
        List<NotificationItem> notifications = new ArrayList<>();
        for (String notification : notificationsSet) {
            String[] parts = notification.split("::");
            if (parts.length == 2) {
                notifications.add(new NotificationItem(parts[0], parts[1]));
            }
        }
        return notifications;
    }

    private void saveNotifications(List<NotificationItem> notifications) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> notificationsSet = new HashSet<>();
        for (NotificationItem notification : notifications) {
            notificationsSet.add(notification.title + "::" + notification.description);
        }
        sharedPreferences.edit().putStringSet(NOTIFICATIONS_KEY, notificationsSet).apply();
    }

    private void checkIfNoNotifications() {
        if (notifications.isEmpty()) {
            noNotificationsText.setVisibility(View.VISIBLE);
            notificationsRecyclerView.setVisibility(View.GONE);
        } else {
            noNotificationsText.setVisibility(View.GONE);
            notificationsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    public static class NotificationItem {
        String title;
        String description;

        NotificationItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.card_enter, R.anim.card_exit);
    }
}
