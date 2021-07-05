package com.example.aislechallenge.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.aislechallenge.R;
import com.example.aislechallenge.databinding.ActivityDashboardBinding;
import com.example.aislechallenge.util.AppConstants;

import java.util.Objects;

import dagger.android.support.DaggerAppCompatActivity;

public class DashboardActivity extends DaggerAppCompatActivity {

    //TODO Implement bottom navigation to open other fragments
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDashboardBinding binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.dashboard_nav_host);
        NavigationUI.setupWithNavController(binding.navView, navController);

        getSupportFragmentManager().setFragmentResultListener(AppConstants.DASHBOARD_CALLBACK_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                int likes = bundle.getInt(AppConstants.LIKES_COUNT);
                int notes = bundle.getInt(AppConstants.NOTES_COUNT);

                // Do something with the result
                binding.navView.getOrCreateBadge(R.id.navigation_matches).setNumber(likes);
                Objects.requireNonNull(binding.navView.getBadge(R.id.navigation_matches)).setBackgroundColor(
                        ContextCompat.getColor(DashboardActivity.this,R.color.purple_700));
                binding.navView.getOrCreateBadge(R.id.navigation_notes).setNumber(notes);
                Objects.requireNonNull(binding.navView.getBadge(R.id.navigation_notes)).setBackgroundColor(
                        ContextCompat.getColor(DashboardActivity.this,R.color.purple_700));
            }
        });
    }

}