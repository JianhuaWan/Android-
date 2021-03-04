package consultan.vanke.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * create by zj on 2018/8/3
 */
public class NetUtils {
    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;


    public static int getNetState(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager .getActiveNetworkInfo();


        if (activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
            if (activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                return  NETWORK_WIFI;

            }else if (activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                return NETWORK_MOBILE;
            }

        }else{
            return NETWORK_NONE;
        }

        return NETWORK_NONE;

    }



}
