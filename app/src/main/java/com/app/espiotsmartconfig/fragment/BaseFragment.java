package com.app.espiotsmartconfig.fragment;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import static com.app.espiotsmartconfig.cache.Const.SERIALIZABLE;

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    public static BaseFragment newInstance(Class fragmentClass, Parcelable serializable) {
        BaseFragment baseFragment = null;
        try {
            baseFragment = (BaseFragment) fragmentClass.newInstance();
            Bundle args = new Bundle();
            args.putParcelable(SERIALIZABLE, serializable);
            baseFragment.setArguments(args);
            return baseFragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseFragment;
    }

}
