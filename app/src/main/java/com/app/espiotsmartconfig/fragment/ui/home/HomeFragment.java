package com.app.espiotsmartconfig.fragment.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.databinding.FragmentHomeBinding;
import com.app.espiotsmartconfig.fragment.BaseFragment;

public class HomeFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        final Switch switchLed =binding.switchLed;
        switchLed.setOnCheckedChangeListener((compoundButton, b) -> {
            BspHelper.pushData(b ? "1" : "0");
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_home);
    }
}