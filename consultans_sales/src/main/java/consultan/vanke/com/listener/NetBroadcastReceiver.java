package consultan.vanke.com.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import consultan.vanke.com.activity.BaseActivity;
import consultan.vanke.com.utils.NetUtils;

public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvent evevt = BaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtils.getNetState(context);
            evevt.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public interface NetEvent {
        void onNetChange(int netWorkState);
    }
}
