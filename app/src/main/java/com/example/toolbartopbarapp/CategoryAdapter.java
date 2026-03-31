package com.example.toolbartopbarapp; // App package (must match your project structure)

import android.content.Context; // Needed to access system services like LayoutInflater
import android.view.LayoutInflater; // Used to inflate XML into View objects
import android.view.View; // Base class for UI elements
import android.view.ViewGroup; // Container for views

import android.widget.TextView; // Used for category title

import androidx.annotation.NonNull; // Annotation to avoid null issues
import androidx.recyclerview.widget.LinearLayoutManager; // Layout manager for RecyclerView
import androidx.recyclerview.widget.RecyclerView; // RecyclerView component

import java.util.List; // List interface

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context; // Holds reference to Activity context
    private List<com.example.toolbartopbarapp.Category> categoryList; // Data source (list of categories)

    public CategoryAdapter(Context context) {
        this.context = context; // Store context for later use
    }

    public void setData(List<com.example.toolbartopbarapp.Category> list) {
        this.categoryList = list; // Assign data to adapter
        notifyDataSetChanged(); // Refresh RecyclerView
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context) // Get LayoutInflater from context
                .inflate(R.layout.item_category, parent, false); // Inflate category layout

        return new CategoryViewHolder(view); // Return ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        if (categoryList == null) return; // Safety check to prevent crash

        com.example.toolbartopbarapp.Category category = categoryList.get(position); // Get current category

        holder.tvNameCategory.setText(category.getNameCategory()); // Set category title

        // Horizontal layout for inner RecyclerView
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);

        holder.rcvAction.setLayoutManager(layoutManager); // Apply layout manager

        holder.rcvAction.setHasFixedSize(true); // Improves performance

        // Set adapter for nested RecyclerView using CardItem list
        com.example.toolbartopbarapp.CardAdapter cardAdapter = new com.example.toolbartopbarapp.CardAdapter(category.getItems());

        holder.rcvAction.setAdapter(cardAdapter); // Attach adapter
    }

    @Override
    public int getItemCount() {
        return (categoryList != null) ? categoryList.size() : 0; // Return size safely
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameCategory; // Category title
        RecyclerView rcvAction; // Horizontal RecyclerView for cards

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tv_name_category); // Link title
            rcvAction = itemView.findViewById(R.id.rcv_action); // Link RecyclerView
        }
    }
}