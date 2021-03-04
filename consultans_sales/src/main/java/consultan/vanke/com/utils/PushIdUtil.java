package consultan.vanke.com.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class PushIdUtil {
    public static String initPushId(Context context) {
        SharedPreferences pre = context.getSharedPreferences("uuid.xml", Context.MODE_PRIVATE);
        String uuid = pre.getString("uuid", "");
        if (!TextUtils.isEmpty(uuid)) {
            return uuid;
        } else {
            uuid = new DeviceUuidFactory(context).getUuid().toString();
            if (!TextUtils.isEmpty(uuid)) {
                pre.edit().putString("uuid", uuid).commit();
                return uuid;
            } else {
                uuid = DeviceUuidFactory.Installation.sID;
                pre.edit().putString("uuid", uuid).commit();
                return uuid;
            }
        }
    }
}
