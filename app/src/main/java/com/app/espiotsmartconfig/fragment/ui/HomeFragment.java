package com.app.espiotsmartconfig.fragment.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.espiotsmartconfig.Adapter.DeviceAdapter;
import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.model.Device;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Device> deviceList = new ArrayList<>();
    private DeviceAdapter deviceAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        deviceList.addAll(Const.getDeviceDataSource(getContext()));
        deviceAdapter = new DeviceAdapter(deviceList, getContext());
        recyclerView.setAdapter(deviceAdapter);
    }
}