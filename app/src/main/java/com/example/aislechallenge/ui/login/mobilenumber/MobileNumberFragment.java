package com.example.aislechallenge.ui.login.mobilenumber;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.aislechallenge.R;
import com.example.aislechallenge.databinding.MobileNumberFragmentBinding;
import com.example.aislechallenge.util.AppConstants;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;


public class MobileNumberFragment extends Fragment {

    @Inject
    public DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    public MobileNumberViewModel mViewModel;

    private MobileNumberFragmentBinding mMobileNumberFragmentBinding;

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
        mMobileNumberFragmentBinding = MobileNumberFragmentBinding.inflate(inflater, container, false);
        return mMobileNumberFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavController = Navigation.findNavController(view);
        setupUI();
        setupObservers();
        mViewModel.checkLoginStatus();
    }

    private void setupUI() {
        imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mMobileNumberFragmentBinding.button.setOnClickListener(v -> {
            hideKeyboard(mMobileNumberFragmentBinding.etPhoneNumber);
            mViewModel.isValidPhoneNumber(mMobileNumberFragmentBinding.etPhoneNumber.getText().toString());

        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupObservers() {
        mViewModel.getIsPhoneNumberValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid.equalsIgnoreCase("error")) {
                mMobileNumberFragmentBinding.etPhoneNumber.setError("Enter Valid Number");
            } else {
                String sb = mMobileNumberFragmentBinding.textView3.getText().toString() +
                        isValid;
                mViewModel.verifyPhoneNumber(sb);
            }
        });

        mViewModel.getApiCallStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case ERROR:
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    Bundle bundle = new Bundle();
                    String sb = mMobileNumberFragmentBinding.textView3.getText().toString() +
                            mMobileNumberFragmentBinding.etPhoneNumber.getText().toString();
                    bundle.putString(AppConstants.MOBILE_NUMBER, sb);
                    mNavController.navigate(R.id.action_mobileNumberFragment_to_OTPFragment, bundle);
                    break;
            }
        });


        mViewModel.getLoginStatus().observe(getViewLifecycleOwner(), status -> {
            if (status) {
                mNavController.navigate(R.id.action_mobileNumberFragment_to_dashboardActivity);
                requireActivity().finish();
            }
        });
    }

    public void hideKeyboard(View view) {
        try {
            imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } catch (Exception ignored) {
        }
    }


}