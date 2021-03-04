package consultan.vanke.com.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import consultan.vanke.com.base.BaseApplication;

public class ToastUtils {
    private static Context context = BaseApplication.getInstance();
    private static Toast toast;

    public static void show(int resId) {
        show(context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(String resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
