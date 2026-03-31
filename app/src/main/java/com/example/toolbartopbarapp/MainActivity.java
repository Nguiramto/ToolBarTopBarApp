package com.example.toolbartopbarapp;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Install splash screen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);


        // Exit Splash screen animation
        splashScreen.setOnExitAnimationListener(splashView -> {

            // Get splash screen view
            View view = splashView.getView();

            // Create slide-up animation (moves view up)
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    view,
                    View.TRANSLATION_Y,
                    0f,
                    -view.getHeight()
            );

            // Animation speed (0.6 seconds)
            slideUp.setDuration(600); // Animation speed (0.6 seconds)

            slideUp.start(); // Start animation

            slideUp.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    splashView.remove(); // Remove splash after animation ends
                }
            });
        });

        // Calls parent implementation
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // ⚡ Must set content view before findViewById
        setContentView(R.layout.activity_main); // Make sure this is your DrawerLayout XML

        // Initialize UI components
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);

        // Set up drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Load default fragment (HomeFragment)
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // BottomNavigationView setup
        bottomNavigationView.setBackground(null); // Remove background for FAB overlay
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.search_treatment) {
                replaceFragment(new SearchFragment());
            } else if (id == R.id.book_appointment) {
                replaceFragment(new AppointmentFragment());
            } else if (id == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });

        // FAB click listener
        fab.setOnClickListener(view -> showBottomDialog());

        // Optional: navigation drawer item click listener (if you want it functional)
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            }
            // Add more else-if blocks here for other nav items if needed

            drawerLayout.closeDrawers();
            return true;
        });
    }

    // Replace fragment helper method
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Bottom sheet dialog for FAB actions
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout searchLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout appointmentLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout consultLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        searchLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(this, "Search Treatments clicked", Toast.LENGTH_SHORT).show();
        });

        appointmentLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(this, "Book Appointment clicked", Toast.LENGTH_SHORT).show();
        });

        consultLayout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(this, "Consult a Doctor clicked", Toast.LENGTH_SHORT).show();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}