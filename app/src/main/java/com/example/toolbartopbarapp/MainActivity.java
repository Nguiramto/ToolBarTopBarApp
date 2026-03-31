package com.example.toolbartopbarapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    // Floating Action Button
    FloatingActionButton fab;
    // Drawer layout for navigation drawer
    DrawerLayout drawerLayout;
    // Bottom navigation bar
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Link UI components
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set toolbar as app bar
        setSupportActionBar(toolbar);

        // Set up hamburger menu toggle for drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load HomeFragment by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        replaceFragment(new HomeFragment());

        // Remove background for BottomNavigationView to allow FAB overlay
        bottomNavigationView.setBackground(null);

        // Bottom navigation item click listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.search_treatment) {
                replaceFragment(new SearchFragment()); // For searching medical treatments
            } else if (id == R.id.book_appointment) {
                replaceFragment(new AppointmentFragment()); // For booking appointments
            } else if (id == R.id.profile) {
                replaceFragment(new ProfileFragment()); // User profile section
            }

            return true;
        });

        // Floating Action Button click opens bottom sheet
        fab.setOnClickListener(view -> showBottomDialog());
    }

    // Helper method to replace fragments in frame layout
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Show bottom sheet with medical actions
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        // Link layouts inside bottom sheet
        LinearLayout searchLayout = dialog.findViewById(R.id.layoutVideo); // Search Treatments
        LinearLayout appointmentLayout = dialog.findViewById(R.id.layoutShorts); // Book Appointment
        LinearLayout consultLayout = dialog.findViewById(R.id.layoutLive); // Consult a Doctor
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        // Search Treatments action
        searchLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Search Treatments clicked", Toast.LENGTH_SHORT).show();
        });

        // Book Appointment action
        appointmentLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Book Appointment clicked", Toast.LENGTH_SHORT).show();
        });

        // Consult a Doctor action
        consultLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Consult a Doctor clicked", Toast.LENGTH_SHORT).show();
        });

        // Close bottom sheet
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // Set dialog appearance
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}