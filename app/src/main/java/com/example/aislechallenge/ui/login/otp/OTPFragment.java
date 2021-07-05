package com.example.aislechallenge.ui.login.otp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aislechallenge.R;
import com.example.aislechallenge.databinding.OtpFragmentBinding;
import com.example.aislechallenge.util.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public class OTPFragment extends Fragment {

    private static final String TAG = OTPFragment.class.getName();
    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    public OTPViewModel mViewModel;


    private OtpFragmentBinding mOtpFragmentBinding;
    private NavController mNavController;
    private InputMethodManager imm;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mOtpFragmentBinding = OtpFragmentBinding.inflate(inflater, container, false);
        return mOtpFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mOtpFragmentBinding.tvMobileNumber.setText(bundle.getString(AppConstants.MOBILE_NUMBER));
        }
        imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mNavController = Navigation.findNavController(view);
        setupUI();
        setupObservers();
        mViewModel.startCountdown();

    }

    private void setupUI() {
        mOtpFragmentBinding.button.setOnClickListener(v -> {
            hideKeyboard(mOtpFragmentBinding.etOtp);
            mViewModel.verifyOtp(mOtpFragmentBinding.tvMobileNumber.getText().toString(), mOtpFragmentBinding.etOtp.getText().toString());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupObservers() {
        mViewModel.getApiCallStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case SUCCESS:
                    showError(false);
                    mNavController.navigate(R.id.action_OTPFragment_to_dashboardActivity);
                    requireActivity().finish();
                    break;
                case LOADING:
                    showError(false);
                    break;
                case ERROR:
                    showError(true);
                    break;
            }
        });

        mViewModel.getCountDownStatus().observe(getViewLifecycleOwner(), time -> {
            if (time.isEmpty()) {
                //Handle Retry Flow
                Log.i(TAG, "Handle Retry Flow");
            } else {
                mOtpFragmentBinding.tvCountDown.setText(time);
            }
        });
    }

    //Show network error window to try again
    private void showError(boolean isVisible) {
        mOtpFragmentBinding.networkError.networkRootLayout.bringToFront();
        mOtpFragmentBinding.networkError.networkRootLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void hideKeyboard(View view) {
        try {
            imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch(Exception ignored) {
        }
    }
}