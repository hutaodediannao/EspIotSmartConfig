package com.app.espiotsmartconfig;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.app.espiotsmartconfig.Adapter.MainFragmentPagerAdapter;
import com.app.espiotsmartconfig.activity.SmargConfigActivity;
import com.app.espiotsmartconfig.base.BaseActivity;
import com.app.espiotsmartconfig.fragment.BaseFragment;
import com.app.espiotsmartconfig.fragment.ui.DashboardFragment;
import com.app.espiotsmartconfig.fragment.ui.HomeFragment;
import com.app.espiotsmartconfig.fragment.ui.NotificationsFragment;
import com.app.espiotsmartconfig.model.EventMsg;
import com.app.espiotsmartconfig.widget.HeaderView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ED_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ERROR_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_FAILED_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.CONNECT_ING_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.RECEIVE_MESSAGE_CODE;
import static com.app.espiotsmartconfig.model.EventMsg.TOAST_CODE;

/**
 * 作者:胡涛
 * 日期:2021-7-22
 * 时间:0:14
 * 功能:主页
 */
public class MainActivity extends BaseActivity {

    private ProgressDialog loadingDialog;
    private MainFragmentPagerAdapter fragmentPagerAdapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private ViewPager viewPager;
    private List<Integer> bottomNavList = new ArrayList<>();
    private List<String> titleNavList = new ArrayList<>();
    private AlertDialog tipsDialog;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        HeaderView mHeaderView = findViewById(R.id.header);
        mHeaderView.setHeaderClickListener(() -> startActivity(new Intent(this, SmargConfigActivity.class)));

        viewPager = findViewById(R.id.viewPager);
        fragmentList.addAll(Arrays.asList(
                new HomeFragment(),
                new DashboardFragment(),
                new NotificationsFragment()
        ));
        fragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(fragmentPagerAdapter);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    mHeaderView.setTitle(titleNavList.get(0)).setMenuVisible(true);
                    break;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    mHeaderView.setTitle(titleNavList.get(1)).setMenuVisible(false);
                    break;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    mHeaderView.setTitle(titleNavList.get(2)).setMenuVisible(false);
                    break;
                default:
                    break;
            }
            return false;
        });

        bottomNavList.addAll(Arrays.asList(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications
        ));
        titleNavList.addAll(Arrays.asList(
                getString(R.string.title_home),
                getString(R.string.title_dashboard),
                getString(R.string.title_notifications)
        ));
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mHeaderView.setTitle(titleNavList.get(position)).setMenuVisible(position == 0);
                navView.setSelectedItemId(bottomNavList.get(position));
            }
        });
    }

    @Override
    public boolean needRegistEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(EventMsg msg) {
        if (mWeakReference.get() != null && !mWeakReference.get().isFinishing()) {
            switch (msg.code) {
                case TOAST_CODE:
                    String dataStr = msg.data;
                    showToast(dataStr);
                case RECEIVE_MESSAGE_CODE:
                    //接收到指令
                    String data = msg.data;
                    if (data != null && !data.trim().equals("")) {
                        //指令执行成功
                    }
//                    showToast(data);
                    break;
                case CONNECT_ING_CODE:
                    if (loadingDialog == null) {
                        loadingDialog = ProgressDialog.
                                show(MainActivity.this, "服务初始化", "正在连接服务器...");
                        loadingDialog.setCancelable(true);
                    } else {
                        loadingDialog.show();
                    }
                    break;
                case CONNECT_ED_CODE:
                case CONNECT_ERROR_CODE:
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                    }
                    break;
                case CONNECT_FAILED_CODE:
//                    if (tipsDialog == null) {
//                        tipsDialog = new AlertDialog.Builder(this).setTitle("连接已断开").setMessage("是否重试?")
//                                .setCancelable(true)
//                                .setPositiveButton("确定", (dialogInterface, i) -> {
//                                    tipsDialog.dismiss();
//                                    //重新连接机制
//                                    EspApp.getInstance().getServiceConnection().getMqttService().doClientConnection();
//                                }).setNegativeButton("取消", (dialogInterface, i) -> {
//                                    tipsDialog.dismiss();
//                                }).create();
//                    }
//                    tipsDialog.show();
                    showToast("连接发生了异常，请检查网络后重试!");
                    break;
                default:
                    break;
            }
        }
    }

}