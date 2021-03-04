package consultan.vanke.com.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.githang.statusbar.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import consultan.vanke.com.BuildConfig;
import consultan.vanke.com.listener.NetBroadcastReceiver;
import consultan.vanke.com.utils.EventBusUtils;
import consultan.vanke.com.utils.NetUtils;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends SupportActivity implements NetBroadcastReceiver.NetEvent {
    protected Context mContext;
    protected String simpleName;
    Unbinder unbinder;
    //网络监听
    public static NetBroadcastReceiver.NetEvent evevt;
    public static int netState;
    NetBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleName = this.getClass().getSimpleName();//得到当前类名
        //正式环境禁止屏幕截屏
        if (!BuildConfig.DEBUG) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        if (isRegisterEventBus()) {
            EventBusUtils.register(this);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initContentView(savedInstanceState);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        initElements();
        initData();
        initEvent();
        evevt = this;
        this.netState = NetUtils.getNetState(this);
        isConnectNet();
        //动态注册
        receiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    //设置状态栏颜色
    public void setStatusBarColors(int color) {
        StatusBarCompat.setStatusBarColor(this, color, true);
    }

    //是否注册eventbus
    public boolean isRegisterEventBus() {
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (isRegisterEventBus()) {
            EventBusUtils.unRegister(this);
        }
        evevt = null;
        unregisterReceiver(receiver);
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    protected abstract void initContentView(Bundle bundle);

    protected abstract void initElements();

    protected abstract void initData();

    protected abstract void initEvent();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onNetChange(int netWorkState) {
        this.netState = netWorkState;
        isConnectNet();
    }


    public static boolean isConnectNet() {
        if (netState == NetUtils.NETWORK_NONE) {
            return false;
        } else {
            return true;
        }

    }
}
