package com.app.espiotsmartconfig.fragment.deviceFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.espiotsmartconfig.Adapter.BaseRecyclerAdapter;
import com.app.espiotsmartconfig.Adapter.LedMatrixAdapter;
import com.app.espiotsmartconfig.R;
import com.app.espiotsmartconfig.cache.Const;
import com.app.espiotsmartconfig.core.BspHelper;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.model.Device;
import com.app.espiotsmartconfig.model.LedDevice;
import com.app.espiotsmartconfig.widget.HeaderView;
import com.app.espiotsmartconfig.widget.LedMatrixView;

import org.json.JSONArray;

import java.util.List;

import static com.app.espiotsmartconfig.cache.Const.LED;

/**
 * 作者:胡涛
 * 日期:2021-7-28
 * 时间:23:22
 * 功能:云台控制
 */
public class LedFragment extends BaseFragment {

    private Device mDevice;
    private View mIvClear;
    private LedMatrixView mLedMatrixView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDevice = getArguments().getParcelable(Const.SERIALIZABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_led, container, false);
        HeaderView mHeaderView = rootView.findViewById(R.id.header);
        mHeaderView.setTitle(mDevice.getName())
                .setBackVisible(true)
                .setMenuVisible(false);
        mIvClear = rootView.findViewById(R.id.ivClear);
        mLedMatrixView = rootView.findViewById(R.id.ledMatrixView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIvClear.setOnClickListener(v -> {
            mLedMatrixView.resetAll();
            BspHelper.pushData("CLEAR");
        });
        mLedMatrixView.setPushCallback(list -> {
            BspHelper.pushData("LIST:" + list.toString());
        });
    }
}