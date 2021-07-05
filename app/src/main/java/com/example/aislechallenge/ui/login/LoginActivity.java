package com.example.aislechallenge.ui.login;

import android.os.Bundle;
import com.example.aislechallenge.databinding.ActivityLoginBinding;
import com.example.aislechallenge.util.AppPreferencesHelper;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

/**
 *  LoginActivity is launched when user is not logged in.
 */
public class LoginActivity extends DaggerAppCompatActivity {

    @Inject
    AppPreferencesHelper appPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}