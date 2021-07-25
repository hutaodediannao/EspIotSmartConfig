package com.app.espiotsmartconfig.fragment.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Boolean> mSwitchLed;

    public HomeViewModel() {

        mSwitchLed = new MutableLiveData<>();
        mSwitchLed.setValue(false);
    }

    public void setSwitchLed(boolean value) {
        mSwitchLed.setValue(value);
    }
}