package consultan.vanke.com.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by huangb11 on 2017/5/26.
 * Activity堆栈管理
 */

public class ActivityManagerUtil {
    private static ActivityManagerUtil INSTANCE;

    private Stack<Activity> mActivityStack;

    public static ActivityManagerUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityManagerUtil();
        }
        return INSTANCE;
    }

    private ActivityManagerUtil() {
        mActivityStack = new Stack<Activity>();
    }

    public void addActivity(Activity activity) {
        mActivityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        mActivityStack.remove(activity);
    }

    public void removeActivity() {
        mActivityStack.pop();
    }

    public Activity getActivity(int index) {
        return mActivityStack.get(index);
    }

    public Activity getPop() {
        return getActivity(0);
    }

    public int getCount() {
        return mActivityStack.size();
    }

    public void finishAll() {
        for (Activity activity : mActivityStack) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void clear() {
        mActivityStack.clear();
    }

    public Stack<Activity> getmActivityStack() {
        return mActivityStack;
    }

    public void setmActivityStack(Stack<Activity> mActivityStack) {
        this.mActivityStack = mActivityStack;
    }

    // 查找栈中是否存在指定的activity
    public boolean checkActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    // finish栈中是否存在指定的activity
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }
}
