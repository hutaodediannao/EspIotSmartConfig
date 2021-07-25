package com.app.espiotsmartconfig.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.espiotsmartconfig.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragmentList;

    public MainFragmentPagerAdapter(@NonNull @NotNull FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
