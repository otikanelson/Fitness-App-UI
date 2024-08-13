package com.example.frebfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<notifications.NotificationItem> notifications;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public NotificationsAdapter(Context context, List<notifications.NotificationItem> notifications, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.notifications = notifications;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_notification, parent, false); // Updated layout name
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        notifications.NotificationItem notification = notifications.get(position);
        holder.notificationTitleTextView.setText(notification.title);
        holder.notificationDescriptionTextView.setText(notification.description);
        holder.deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView notificationTitleTextView;
        TextView notificationDescriptionTextView;
        Button deleteButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitleTextView = itemView.findViewById(R.id.notification_title);
            notificationDescriptionTextView = itemView.findViewById(R.id.notification_description);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
