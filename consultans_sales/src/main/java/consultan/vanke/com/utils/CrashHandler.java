package consultan.vanke.com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.dianping.logan.Logan;

import consultan.vanke.com.activity.WelcomeActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 作用:
 * 1.收集错误信息
 * 2.保存错误信息
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = null;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    // 保存手机信息和异常信息
    private Map<String, String> mMessage = new HashMap<>();

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    synchronized (CrashHandler.class) {
                        sInstance = new CrashHandler();
                    }
                }
            }
        }
        return sInstance;
    }

    private CrashHandler() {
    }

    /**
     * 初始化默认异常捕获
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        // 获取默认异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将此类设为默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handleException(e);
    }

    /**
     * 是否人为捕获异常
     *
     * @param e Throwable
     * @return true:已处理 false:未处理
     */
    private boolean handleException(Throwable e) {
        collectErrorMessages();
        saveErrorMessages(e);
        //应用异常时候重启程序
        Intent intent = new Intent(mContext, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        // 杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        return true;
    }

    /**
     * 1.收集错误信息
     */
    private void collectErrorMessages() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = TextUtils.isEmpty(pi.versionName) ? "null" : pi.versionName;
                String versionCode = "" + pi.versionCode;
                mMessage.put("versionName", versionName);
                mMessage.put("versionCode", versionCode);
            }
            // 通过反射拿到错误信息
            Field[] fields = Build.class.getFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        mMessage.put(field.getName(), field.get(null).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2.保存错误信息
     *
     * @param e Throwable
     */
    private void saveErrorMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : mMessage.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
//        Throwable cause = e.getCause();
        // 循环取出Cause
//        while (cause != null) {
//            cause.printStackTrace(pw);
//            cause = e.getCause();
//        }
        pw.close();
        String result = writer.toString();
        sb.append(result);
        //日志保存本地不可直接查看,这里统计的主要是java层奔溃
        Logan.w(sb.toString(), 0);
        Logan.f();
    }

}
