package com.example.aislechallenge.ui.dashboard.discovery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aislechallenge.R;
import com.example.aislechallenge.databinding.DiscoveryFragmentBinding;
import com.example.aislechallenge.util.ImageLoaderUtil;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

import static com.example.aislechallenge.util.AppConstants.DASHBOARD_CALLBACK_KEY;
import static com.example.aislechallenge.util.AppConstants.LIKES_COUNT;
import static com.example.aislechallenge.util.AppConstants.NOTES_COUNT;

public class DiscoveryFragment extends Fragment {

    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    public DiscoveryViewModel mViewModel;

    @Inject
    public ImageLoaderUtil mImageLoaderUtil;

    private DiscoveryFragmentBinding mDiscoveryFragmentBinding;


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDiscoveryFragmentBinding = DiscoveryFragmentBinding.inflate(inflater, container, false);
        return mDiscoveryFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.fetchProfiles();
        setupUI();
        setupObservers();
    }

    private void setupUI() {
        mDiscoveryFragmentBinding.networkError.button3.setOnClickListener(v -> {
            mViewModel.fetchProfiles();
        });
    }

    private void setupObservers() {

        mViewModel.getApiCallStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case SUCCESS:
                    showScreen(true);
                    showLoading(false);
                    showError(false);
                    break;
                case LOADING:
                    showScreen(false);
                    showLoading(true);
                    showError(false);
                    break;
                case ERROR:
                    showScreen(false);
                    showLoading(false);
                    showError(true);
                    break;
            }
        });


        //Set UI data
        mViewModel.getProfiles().observe(getViewLifecycleOwner(), response -> {
            mImageLoaderUtil.loadImage(mDiscoveryFragmentBinding.userAvatar, response.invites.profiles.get(0).photos.get(0).photo, false);
            mImageLoaderUtil.loadImage(mDiscoveryFragmentBinding.userAvatar1, response.likes.profiles.get(0).avatar, true);
            mImageLoaderUtil.loadImage(mDiscoveryFragmentBinding.userAvatar2, response.likes.profiles.get(1).avatar, true);
            String title = response.invites.profiles.get(0).general_information.first_name + " , " + response.invites.profiles.get(0).general_information.age;
            mDiscoveryFragmentBinding.userName.setText(title);
            mDiscoveryFragmentBinding.userAge.setText(getString(R.string.notes_text, String.valueOf(response.invites.pending_invitations_count)));
            mDiscoveryFragmentBinding.userName2.setText(response.likes.profiles.get(0).firstName);
            mDiscoveryFragmentBinding.userName3.setText(response.likes.profiles.get(1).firstName);

            Bundle result = new Bundle();
            result.putInt(NOTES_COUNT, response.invites.pending_invitations_count);
            result.putInt(LIKES_COUNT, response.likes.likes_received_count);
            requireActivity().getSupportFragmentManager().setFragmentResult(DASHBOARD_CALLBACK_KEY, result);
        });
    }

    // Show Network error screen.
    private void showError(boolean isVisible) {
        mDiscoveryFragmentBinding.networkError.networkRootLayout.bringToFront();
        mDiscoveryFragmentBinding.networkError.networkRootLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    // Show loading screen.
    private void showLoading(boolean isVisible) {
        mDiscoveryFragmentBinding.loading.rootLayout.bringToFront();
        mDiscoveryFragmentBinding.loading.rootLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    // Show data screen after successful response is received.
    private void showScreen(boolean isVisible) {
        mDiscoveryFragmentBinding.discoveryLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    public interface DisplayCount {
        void onCountReceived(int likes, int notes);

    }
}